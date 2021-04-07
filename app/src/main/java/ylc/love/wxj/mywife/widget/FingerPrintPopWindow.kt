package ylc.love.wxj.mywife.widget

import android.annotation.SuppressLint
import android.app.KeyguardManager
import android.content.Context
import android.content.Context.FINGERPRINT_SERVICE
import android.content.Context.KEYGUARD_SERVICE
import android.hardware.fingerprint.FingerprintManager
import android.os.Build
import android.os.CancellationSignal
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import razerdp.basepopup.BasePopupWindow
import ylc.love.wxj.mywife.R
import ylc.love.wxj.mywife.databinding.PopWindowFingerPrintBinding
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey


/**
 * @author Administrator
 * @create on 2021/1/28 0028
 * 说明: 指纹验证弹窗
 */
@RequiresApi(Build.VERSION_CODES.M)
class FingerPrintPopWindow(context: Context) : BasePopupWindow(context), View.OnClickListener {
    private lateinit var bind: PopWindowFingerPrintBinding
    private var mCancellationSignal: CancellationSignal? = null
    private var isSelfCancelled: Boolean = false
    private var fingerprintManager: FingerprintManager =
        (context.getSystemService(FINGERPRINT_SERVICE) as FingerprintManager?)!!
    private var keyguardManager: KeyguardManager =
        (context.getSystemService(KEYGUARD_SERVICE) as KeyguardManager)
    private val DEFAULT_KEY_NAME = "default_key"

    init {
        initKey()
        initCipher()
    }

    override fun onCreateContentView(): View {
        val view = createPopupById(R.layout.pop_window_finger_print)
        bind = DataBindingUtil.bind(view)!!
        bind.ivCancel.setOnClickListener(this)
        bind.ivFingerPrint.setOnClickListener(this)
        return bind.root
    }

    @SuppressLint("SetTextI18n")
    private fun checkFinger(): Boolean {
        try {
            // Check if the fingerprint sensor is present
            if (!fingerprintManager.isHardwareDetected) {
                // Update the UI with a message
                bind.msg.text = "不支持指纹验证"
                return false
            }
            if (!fingerprintManager.hasEnrolledFingerprints()) {
                bind.msg.text = "该手机没有设置指纹"
                return false
            }
            if (!keyguardManager.isKeyguardSecure) {
                bind.msg.text = "没有开启指纹功能"
                return false
            }
        } catch (se: SecurityException) {
            se.printStackTrace()
            return false
        }
        return true
    }

    private fun startListener() {
        isSelfCancelled = false
        mCancellationSignal = CancellationSignal()
        fingerprintManager.authenticate(
            FingerprintManager.CryptoObject(cipher),
            mCancellationSignal,
            0,
            object : FingerprintManager.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    if (!isSelfCancelled) {
                        bind.msg.text = errString
                        if (errorCode == FingerprintManager.FINGERPRINT_ERROR_LOCKOUT) {
                            dismiss()
                            listener?.onCancel()
                        }
                    }
                }

                override fun onAuthenticationHelp(helpCode: Int, helpString: CharSequence) {
                    bind.msg.text = helpString
                }

                override fun onAuthenticationSucceeded(result: FingerprintManager.AuthenticationResult) {
                    bind.msg.text = "指纹认证成功"
                    listener?.onOk()
                }

                override fun onAuthenticationFailed() {
                    bind.msg.text = "指纹认证失败，请再试一次"
                }
            },
            null
        )
    }

    override fun onDismiss() {
        super.onDismiss()
        stopListener()
    }

    private fun stopListener() {
        if (mCancellationSignal != null) {
            mCancellationSignal?.cancel()
            mCancellationSignal = null
            isSelfCancelled = true
        }
    }

    override fun onWindowFocusChanged(popupDecorViewProxy: View?, hasWindowFocus: Boolean) {
        super.onWindowFocusChanged(popupDecorViewProxy, hasWindowFocus)
        if (hasWindowFocus && checkFinger()) {
            startListener()
        }
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.iv_cancel -> {
                listener?.onCancel()
                dismiss()
            }
            R.id.iv_finger_print -> {
                if (checkFinger()) {
                    startListener()
                }
            }
        }
    }

    private var keyStore: KeyStore? = null
    private lateinit var cipher: Cipher

    @RequiresApi(Build.VERSION_CODES.M)
    private fun initKey() {
        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore")
            keyStore?.load(null)
            val keyGenerator: KeyGenerator =
                KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
            val builder = KeyGenParameterSpec.Builder(
                DEFAULT_KEY_NAME, KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
            )
                .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                .setUserAuthenticationRequired(true)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
            keyGenerator.init(builder.build())
            keyGenerator.generateKey()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun initCipher() {
        try {
            val key: SecretKey = keyStore?.getKey(DEFAULT_KEY_NAME, null) as SecretKey
            cipher = Cipher.getInstance(
                KeyProperties.KEY_ALGORITHM_AES + "/"
                        + KeyProperties.BLOCK_MODE_CBC + "/"
                        + KeyProperties.ENCRYPTION_PADDING_PKCS7
            )
            cipher.init(Cipher.ENCRYPT_MODE, key)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    var listener: ClickListener? = null

    interface ClickListener {
        fun onCancel()
        fun onOk()
    }

}