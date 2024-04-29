import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.appsamurai.storyly.databinding.GreenBottomSheetBinding
import com.appsamurai.storyly.databinding.RedBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class GreenBottomSheet : BottomSheetDialogFragment() {

    private lateinit var binding: GreenBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = GreenBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.close.setOnClickListener {
            dismiss()
        }
    }
}