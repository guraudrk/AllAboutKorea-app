package fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.allaboutkorea.allaboutkorea.R
import com.allaboutkorea.allaboutkorea.contentslist.contentrvadapter
import com.allaboutkorea.allaboutkorea.databinding.FragmentTalkBinding

import com.google.firebase.database.DatabaseReference


//실질적으로는 갤러리에 관한 사이트이다.

class TalkFragment : Fragment() {
    //프레그먼트 바인딩은 보통의 바인딩과 다르다.
    //binding이라는 변수를 불러 오는 것은 같지만, 불러오는 변수가 다르다.


    lateinit var myRef : DatabaseReference

    lateinit var rvadapter : contentrvadapter

    val bookmarkidlist = mutableListOf<String>()

    private lateinit var binding: FragmentTalkBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_talk,container,false)




        //이제, 바인딩에서 다른 버튼을 클릭을 하면 어떻게 되는지 코드를 작성해 보자.

        binding.tipTap.setOnClickListener {

            it.findNavController().navigate(R.id.action_talkFragment_to_tipFragment)
        }


        binding.homeTap.setOnClickListener {

            it.findNavController().navigate(R.id.action_talkFragment_to_homeFragment)

        }
        binding.bookmarkTap.setOnClickListener {

            it.findNavController().navigate(R.id.action_talkFragment_to_bookmarkFragment)
            //home to talk

        }
        binding.storeTap.setOnClickListener {

            it.findNavController().navigate(R.id.action_talkFragment_to_storeFragment)

        }

        return binding.root
    }


}





