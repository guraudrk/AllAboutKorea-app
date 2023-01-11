package fragment

import android.content.ContentValues
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
import com.allaboutkorea.allaboutkorea.R
import com.allaboutkorea.allaboutkorea.contentslist.BookmarkRVAdapter
import com.allaboutkorea.allaboutkorea.contentslist.contentmodel
import com.allaboutkorea.allaboutkorea.databinding.FragmentBookmarkBinding
import com.allaboutkorea.allaboutkorea.utils.FBAuth
import com.allaboutkorea.allaboutkorea.utils.FBRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener


class BookmarkFragment : Fragment() {


    //프레그먼트 바인딩은 보통의 바인딩과 다르다.
    //binding이라는 변수를 불러 오는 것은 같지만, 불러오는 변수가 다르다.



    private lateinit var binding: FragmentBookmarkBinding
    var twiceupoad = false
    private val TAG = BookmarkFragment::class.java.simpleName


    val bookmarkidlist = mutableListOf<String>()
    val items = ArrayList<contentmodel>()
    val itemkeylist = ArrayList<String>()
    lateinit var rvadapter : BookmarkRVAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_bookmark,container,false)





        //2.사용자가 북마크한 정보를 다 가져온다.
        getbookmarkdata()



        rvadapter = BookmarkRVAdapter(requireContext(),items,itemkeylist,bookmarkidlist)
        val rv : RecyclerView = binding.bookmarkRV
        rv.adapter = rvadapter


        //실제로 보여주게 하는 놈이다. 빼먹지 말자.
        //Kotlin의 경우 Null이 아닌 Context를 전달해주어야 한다면 requireContext()를 사용해야 한다.

        rv.layoutManager = GridLayoutManager(requireContext(),2)







        //이제, 바인딩에서 다른 버튼을 클릭을 하면 어떻게 되는지 코드를 작성해 보자.

        binding.tipTap.setOnClickListener {

            it.findNavController().navigate(R.id.action_bookmarkFragment_to_tipFragment)
        }


        binding.homeTap.setOnClickListener {

            it.findNavController().navigate(R.id.action_bookmarkFragment_to_homeFragment)

        }

        binding.storeTap.setOnClickListener {

            it.findNavController().navigate(R.id.action_bookmarkFragment_to_storeFragment)

        }

        return binding.root
    }



    private fun getCategoryData(){
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                //데이터를 더하는 불러오는 역활.
                for(dataModel in dataSnapshot.children){
                    Log.d(TAG,dataModel.toString())

                    val item = dataModel.getValue(contentmodel::class.java)
                    //3.전체 중에서 사용자가 북마크한 것만을 보여준다!
                    //북마크가 된 놈만 북마크 리스트에 넣는 코드
                    if(bookmarkidlist.contains(dataModel.key.toString()))
                    {
                        items.add(item!!)
                        itemkeylist.add(dataModel.key.toString())
                    }





                }
                rvadapter.notifyDataSetChanged()




            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        FBRef.category_economy.addValueEventListener(postListener)
        FBRef.category_food.addValueEventListener(postListener)
        FBRef.category_k_drama.addValueEventListener(postListener)
        FBRef.category_funfacts.addValueEventListener(postListener)
        FBRef.category_history.addValueEventListener(postListener)
        FBRef.category_k_beauty.addValueEventListener(postListener)
        FBRef.category_k_pop.addValueEventListener(postListener)
        FBRef.category_korean.addValueEventListener(postListener)
        FBRef.category_travel.addValueEventListener(postListener)


    }


    private fun getbookmarkdata(){


        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                //북마크를 동적으로 삭제하기 위한 코드.



                bookmarkidlist.clear()

                //데이터를 더하는 불러오는 역활.
                for(dataModel in dataSnapshot.children){

                    Log.e(TAG,dataModel.toString())
                    bookmarkidlist.add(dataModel.key.toString())
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

