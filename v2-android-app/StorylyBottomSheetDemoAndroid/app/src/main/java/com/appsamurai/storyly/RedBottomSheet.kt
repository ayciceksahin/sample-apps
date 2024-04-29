import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.appsamurai.storyly.databinding.RedBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class RedBottomSheet : BottomSheetDialogFragment() {

    var onCloseClick : (() -> Unit)? = null

    var onOpenBottomSheet : ((DialogFragment) -> Unit)? = null

    private lateinit var binding: RedBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = RedBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.next.setOnClickListener {
            val modalBottomSheet = GreenBottomSheet()
            onOpenBottomSheet?.invoke(modalBottomSheet)
        }

        binding.close.setOnClickListener {
            dismiss()
            onCloseClick?.invoke()
        }
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        onCloseClick?.invoke()
    }
}