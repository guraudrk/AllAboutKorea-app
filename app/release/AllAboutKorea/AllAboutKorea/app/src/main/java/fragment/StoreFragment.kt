package fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.allaboutkorea.allaboutkorea.R
import com.allaboutkorea.allaboutkorea.databinding.FragmentStoreBinding


class StoreFragment : Fragment() {

    private lateinit var binding : FragmentStoreBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

//        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_store, container, false)

        //우선, 클릭하자 마자 레이아웃 파일을 하나 띄운다.

         binding = DataBindingUtil.inflate(inflater,R.layout.fragment_store, container, false)


        //버튼을 누르면, 웹 사이트로 이동한다.
        //이 코드는 버튼을 누르면 해당 웹 사이트로 이동하는 코드이다.
        binding.koreanetbutton.setOnClickListener {
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.korea.net/"))
            startActivity(intent)

        }








        //이제, 바인딩에서 다른 버튼을 클릭을 하면 어떻게 되는지 코드를 작성해 보자.

        binding.tipTap.setOnClickListener {

            it.findNavController().navigate(R.id.action_storeFragment_to_tipFragment)
        }


        binding.homeTap.setOnClickListener {

            it.findNavController().navigate(R.id.action_storeFragment_to_homeFragment)

        }

        binding.bookmarkTap.setOnClickListener {

            it.findNavController().navigate(R.id.action_storeFragment_to_bookmarkFragment)

        }



        return binding.root
    }

}