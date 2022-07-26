package com.example.appv20

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*



class UserListFragment : Fragment() {
    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userList : ArrayList<User>
    private lateinit var adapter: UserAdapter
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_list, container, false)

    }

    override fun onViewCreated(view : View,savedInstanceState: Bundle?) {
        super.onViewCreated(view,savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        mDbRef = FirebaseDatabase.getInstance().getReference()

        userList = ArrayList()
        adapter =  UserAdapter(getContext()!!,userList)

        userRecyclerView = view.findViewById(R.id.userRecyclerView)

        userRecyclerView.layoutManager = LinearLayoutManager(getContext())
        userRecyclerView.adapter = adapter

        mDbRef.child("user").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                for(postSnapshot in snapshot.children){
                    val currentUser = postSnapshot.getValue(User::class.java)
                    if(mAuth.currentUser?.uid != currentUser?.uid){
                        userList.add(currentUser!!)
                    }

                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })
    }



  /*  override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == com.google.firebase.database.R.id.logout){
            mAuth.signOut()
            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            finish()
            startActivity(intent)

            return true
        }
        return true
    }*/

}