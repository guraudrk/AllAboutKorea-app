package fragment



import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.allaboutkorea.allaboutkorea.*
import com.allaboutkorea.allaboutkorea.contentslist.BookmarkRVAdapter
import com.allaboutkorea.allaboutkorea.contentslist.contentmodel
import com.allaboutkorea.allaboutkorea.databinding.FragmentHomeBinding
import com.allaboutkorea.allaboutkorea.utils.FBAuth
import com.allaboutkorea.allaboutkorea.utils.FBRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener


class HomeFragment : Fragment() {


    private lateinit var binding : FragmentHomeBinding
    //북마크 리스트의 중복 업로드를 막는 코드이다.
    var twiceupoad = false
    private val TAG = HomeFragment::class.java.simpleName

    val bookmarkIdList = mutableListOf<String>()
    val items = ArrayList<contentmodel>()
    val itemKeyList = ArrayList<String>()

    lateinit var rvAdapter : BookmarkRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        Log.d("HomeFragment", "onCreateView")
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)







        getbookmarkdata()


        //상단 아이콘을 클릭을 하면, 그 아이콘으로 이동하는 코드를 작성하자.

        binding.fragmentHomeHistory.setOnClickListener {
            val intent = Intent(context, HistoryActivity::class.java)

            startActivity(intent)

        }

        binding.fragmentHomeFood.setOnClickListener {
            val intent = Intent(context, FoodActivity::class.java)

            startActivity(intent)

        }


        binding.fragmentHomeKpop.setOnClickListener {
            val intent = Intent(context, KpopActivity::class.java)
            startActivity(intent)

        }

        binding.fragmentHomeKdrama.setOnClickListener {
            val intent = Intent(context, KdramaActivity::class.java)
            startActivity(intent)

        }

        binding.fragmentHomeKorean.setOnClickListener {
            val intent = Intent(context, KoreanActivity::class.java)
            startActivity(intent)

        }

        binding.fragmentHomeTravel.setOnClickListener {
            val intent = Intent(context, TravelActivity2::class.java)
            startActivity(intent)

        }

        binding.fragmentHomeFunfacts.setOnClickListener {
            val intent = Intent(context, FunfactsActivity::class.java)
            startActivity(intent)

        }

        binding.fragmentHomeEconomy.setOnClickListener {
            val intent = Intent(context, EconomyActivity::class.java)
            startActivity(intent)

        }

        binding.fragmentHomeKbeauty.setOnClickListener {
            val intent = Intent(context, KbeautyActivity::class.java)
            startActivity(intent)

        }


        //하단 아이콘을 클릭을 하면, 그 아이콘으로 이동하는 코드를 작성하자.



        binding.tipTap.setOnClickListener {
            Log.d("HomeFragment", "tipTap")
            it.findNavController().navigate(R.id.action_homeFragment_to_tipFragment)
        }



        binding.bookmarkTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_bookmarkFragment)
        }

        binding.storeTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_storeFragment)
        }

        rvAdapter = BookmarkRVAdapter(requireContext(), items, itemKeyList, bookmarkIdList)

        val rv : RecyclerView = binding.mainRV
        rv.adapter = rvAdapter

        rv.layoutManager = GridLayoutManager(requireContext(), 2)

        getCategoryData()

        return binding.root
    }

    private fun getCategoryData(){

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (dataModel in dataSnapshot.children) {

                    val item = dataModel.getValue(contentmodel::class.java)

                    items.add(item!!)
                    itemKeyList.add(dataModel.key.toString())


                }

                rvAdapter.notifyDataSetChanged()

            }


            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("ContentListActivity", "loadPost:onCancelled", databaseError.toException())
            }
        }
        FBRef.category_history.addValueEventListener(postListener)
        FBRef.category_food.addValueEventListener(postListener)
        FBRef.category_k_pop.addValueEventListener(postListener)
        FBRef.category_k_drama.addValueEventListener(postListener)
        FBRef.category_korean.addValueEventListener(postListener)
        FBRef.category_travel.addValueEventListener(postListener)
        FBRef.category_funfacts.addValueEventListener(postListener)
        FBRef.category_economy.addValueEventListener(postListener)
        FBRef.category_k_beauty.addValueEventListener(postListener)
    }
    private fun getbookmarkdata(){


        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                //북마크를 동적으로 삭제하기 위한 코드.



                bookmarkIdList.clear()

                //데이터를 더하는 불러오는 역활.
                for(dataModel in dataSnapshot.children){

                    Log.e(TAG,dataModel.toString())
                    bookmarkIdList.add(dataModel.key.toString())
                }

                //1.전체 카테고리에 있는 컨텐츠 데이터들을 다 가져온다.
                //자세한 코드는 여기 안에 있다.
                if(twiceupoad==false) {
                    twiceupoad=true
                    getCategoryData()
                }
                //이렇게 코드를 실행하면, getCategoryData는 getbookmarkdata
                //의 정보를 잘 알기 때문에, 북마크가 아닌 것을 잘 거를 수 있다.


            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }

        //북마크 데이터를 불러오는 코드이다.
        FBRef.bookmarkref.child(FBAuth.getUid()).addValueEventListener(postListener)

    }


}