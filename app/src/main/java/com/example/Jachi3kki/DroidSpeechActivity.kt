package com.example.Jachi3kki

import androidx.appcompat.app.AppCompatActivity
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.Jachi3kki.DroidSpeech.DroidSpeech
import com.example.Jachi3kki.DroidSpeech.OnDSListener
import com.example.Jachi3kki.DroidSpeech.OnDSPermissionsListener
import kotlinx.android.synthetic.main.activity_droid_speech.*
import java.util.*

/**
 * Droid Speech Example Activity
 *
 * @author Vikram Ezhil
 */
class DroidSpeechActivity : Fragment(),
    OnDSListener,
    OnDSPermissionsListener {

    val TAG = "Activity_DroidSpeech"

    private var droidSpeech: DroidSpeech? = null
    private var finalSpeechResult: TextView? = null

    // MARK: Activity Methods
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initializing the droid speech and setting the listener
        droidSpeech = DroidSpeech(requireActivity().applicationContext, requireActivity().getFragmentManager()).apply {
            setOnDroidSpeechListener(this@DroidSpeechActivity)
            setShowRecognitionProgressView(false)
            setOneStepResultVerify(true)
            setRecognitionProgressMsgColor(Color.WHITE)
            setOneStepVerifyConfirmTextColor(Color.WHITE)
            setOneStepVerifyRetryTextColor(Color.WHITE)
            setPreferredLanguage("ko-KR")
            println("이건 뜨냐")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_droid_speech, container, false)

        finalSpeechResult = view.findViewById<TextView>(R.id.finalSpeechResult)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        start?.setOnClickListener {
            startClicked()
            print("동작 테스으1")
        }


        stop?.setOnClickListener {
            stopClicked()
            print("동작 테스으2")
        }
    }

    override fun onPause() {
        super.onPause()

        if (stop?.visibility == View.VISIBLE) {
            stop?.performClick()
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        if (stop?.visibility == View.VISIBLE) {
            stop?.performClick()
        }
    }

    private fun startClicked() {
        print("동작 테스으1")
        droidSpeech?.startDroidSpeechRecognition()

        // Setting the view visibilities when droid speech is running
        start?.visibility = View.GONE
        stop?.visibility = View.VISIBLE
    }

    private fun stopClicked() {
        print("동작 테스으2")
        droidSpeech?.closeDroidSpeechOperations()

        stop?.visibility = View.GONE
        start?.visibility = View.VISIBLE
    }

    // MARK: DroidSpeechListener Methods
    override fun onDroidSpeechSupportedLanguages(currentSpeechLanguage: String, supportedSpeechLanguages: List<String>) {
        Log.i(TAG, "Current speech language = $currentSpeechLanguage")
        Log.i(TAG, "Supported speech languages = $supportedSpeechLanguages")
        if (supportedSpeechLanguages.contains("ko-KR")) {
            // Setting the droid speech preferred language as tamil if found
            droidSpeech?.setPreferredLanguage("ko-KR")

            // Setting the confirm and retry text in tamil
            droidSpeech?.setOneStepVerifyConfirmText("check")
            droidSpeech?.setOneStepVerifyRetryText("no")
        }
    }

    override fun onDroidSpeechRmsChanged(rmsChangedValue: Float) {
        // Log.i(TAG, "Rms change value = " + rmsChangedValue);
    }

    override fun onDroidSpeechLiveResult(liveSpeechResult: String) {
        Log.i(TAG, "Live speech result = $liveSpeechResult")
    }

    override fun onDroidSpeechFinalResult(finalSpeechResult: String) {
        // Setting the final speech result
        this.finalSpeechResult?.text = finalSpeechResult
        println("test$finalSpeechResult")
        if (droidSpeech?.continuousSpeechRecognition == true) {
            val colorPallets1 = intArrayOf(Color.RED, Color.GREEN, Color.BLUE, Color.CYAN, Color.MAGENTA)
            val colorPallets2 = intArrayOf(Color.YELLOW, Color.RED, Color.CYAN, Color.BLUE, Color.GREEN)

            // Setting random color pallets to the recognition progress view
            droidSpeech?.setRecognitionProgressViewColors(if (Random().nextInt(2) == 0) colorPallets1 else colorPallets2)
        } else {
            stop?.visibility = View.GONE
            start?.visibility = View.VISIBLE
        }
    }

    override fun onDroidSpeechClosedByUser() {
        stop?.visibility = View.GONE
        start?.visibility = View.VISIBLE
    }

    override fun onDroidSpeechError(errorMsg: String) {
        // Speech error
        stop?.post { // Stop listening
            stop?.performClick()
        }
    }

    // MARK: DroidSpeechPermissionsListener Method
    override fun onDroidSpeechAudioPermissionStatus(audioPermissionGiven: Boolean, errorMsgIfAny: String) {
        if (audioPermissionGiven) {
            start?.post { // Start listening
                start?.performClick()
            }
        } else {
            if (errorMsgIfAny != null) {
                // Permissions error
            }
            stop?.post { // Stop listening
                stop?.performClick()
            }
        }
    }
}