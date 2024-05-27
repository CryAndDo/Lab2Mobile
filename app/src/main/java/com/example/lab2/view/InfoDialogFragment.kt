import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class InfoDialogFragment : DialogFragment() {

    companion object {
        private const val ARG_MESSAGE = "message"

        fun newInstance(message: String): InfoDialogFragment {
            val args = Bundle()
            args.putString(ARG_MESSAGE, message)
            val fragment = InfoDialogFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val message = arguments?.getString(ARG_MESSAGE) ?: ""

        return AlertDialog.Builder(requireContext())
            .setTitle("Information")
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
    }
}
