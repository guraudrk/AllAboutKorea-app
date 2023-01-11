package fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.AllAboutKorea.allaboutkorea.*
import com.AllAboutKorea.allaboutkorea.contentslist.HistoryActivity
import com.AllAboutKorea.allaboutkorea.databinding.FragmentTipBinding


class TipFragment : Fragment() {
    //프레그먼트 바인딩은 보통의 바인딩과 다르다.
    //binding이라는 변수를 불러 오는 것은 같지만, 불러오는 변수가 다르다.


    private lateinit var binding: FragmentTipBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_tip,container,false)


        //카테고리 1을 클릭하면 관련 내용으로 이동하는 코드이다.
        binding.history.setOnClickListener {

            val intent = Intent(context,HistoryActivity::class.java)
            intent.putExtra("category_history","history")
            startActivity(intent)

        }

        //카테고리 2를 클릭하면 관련 내용으로 이동하는 코드이다.
        binding.food.setOnClickListener {
            val intent = Intent(context,FoodActivity::class.java)
            intent.putExtra("category_food","food")
            startActivity(intent)

        }
        //카테고리 3을 클릭하면 관련 내용으로 이동하는 코드이다.
        binding.kpop.setOnClickListener {
            val intent = Intent(context,KpopActivity::class.java)
            intent.putExtra("category_k_pop","k-pop")
            startActivity(intent)

        }
        //카테고리 4을 클릭하면 관련 내용으로 이동하는 코드이다.
        binding.kdrama.setOnClickListener {
            val intent = Intent(context,KdramaActivity::class.java)
            intent.putExtra("category_k_drama","k-drama")
            startActivity(intent)

        }
        //카테고리 5를 클릭하면 관련 내용으로 이동하는 코드이다.
        binding.korean.setOnClickListener {
            val intent = Intent(context,KoreanActivity::class.java)
            intent.putExtra("category_korean","korean")
            startActivity(intent)

        }
        //카테고리 6을 클릭하면 관련 내용으로 이동하는 코드이다.
        binding.travel.setOnClickListener {
            val intent = Intent(context,TravelActivity2::class.java)
            intent.putExtra("category_travel","travel")
            startActivity(intent)

        }
        //카테고리 7을 클릭하면 관련 내용으로 이동하는 코드이다.
        binding.funfacts.setOnClickListener {
            val intent = Intent(context,FunfactsActivity::class.java)
            intent.putExtra("category_funfacts","funfacts")
            startActivity(intent)

        }
        //카테고리 8을 클릭하면 관련 내용으로 이동하는 코드이다.
        binding.economy.setOnClickListener {
            val intent = Intent(context,EconomyActivity::class.java)
            intent.putExtra("category_economy","economy")
            startActivity(intent)

        }
        //카테고리 9를 클릭하면 관련 내용으로 이동하는 코드이다.
        binding.kbeauty.setOnClickListener {
            val intent = Intent(context,KbeautyActivity::class.java)
            intent.putExtra("category_k_beauty","k_beauty")
            startActivity(intent)

        }

        //이제, 바인딩에서 다른 버튼을 클릭을 하면 어떻게 되는지 코드를 작성해 보자.

        binding.bookmarkTap.setOnClickListener {

            it.findNavController().navigate(R.id.action_tipFragment_to_bookmarkFragment)
        }



        binding.homeTap.setOnClickListener {

            it.findNavController().navigate(R.id.action_tipFragment_to_homeFragment)

        }

        binding.storeTap.setOnClickListener {

            it.findNavController().navigate(R.id.action_tipFragment_to_storeFragment)

        }

        return binding.root
    }


}