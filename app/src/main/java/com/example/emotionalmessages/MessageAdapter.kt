package com.example.whizzz.view.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.emotionalmessages.Chats
import com.example.emotionalmessages.R
import java.text.SimpleDateFormat
import java.util.*

class MessageAdapter(
    chatArrayList: ArrayList<Chats>,
    context: Context,
    currentUser_sender: String
) :
    RecyclerView.Adapter<MessageAdapter.MessageHolder>() {
    private val chatArrayList: ArrayList<Chats>
    private val context: Context
    private val currentUser_sender: String
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageHolder {
        return if (viewType == MSG_TYPE_RIGHT_RECEIVED) {
            val view: View =
                LayoutInflater.from(context)
                    .inflate(R.layout.chat_item_right_received, parent, false)
            MessageHolder(view)
        } else {
            val view: View = LayoutInflater.from(context)
                .inflate(R.layout.chat_item_left_received, parent, false)
            MessageHolder(view)
        }
    }

    override fun onBindViewHolder(holder: MessageHolder, position: Int) {
        val chats: Chats = chatArrayList[position]
        val message: String = chats.message
        val timeStamp: String = chats.timestamp
        val isSeen: Boolean = chats.seen
        val intTimeStamp = timeStamp.toLong()
        val time_msg_received = timeStampConversionToTime(intTimeStamp)
        holder.tv_time.text = time_msg_received
        holder.tv_msg.text = message
        if (position == chatArrayList.size - 1) {
            if (isSeen) {
                holder.tv_seen.visibility = View.VISIBLE
                val seen = "Seen"
                holder.tv_seen.text = seen
            } else {
                holder.tv_seen.visibility = View.VISIBLE
                val delivered = "Delivered"
                holder.tv_seen.text = delivered
            }
        } else {
            holder.tv_seen.visibility = View.GONE
        }
    }

    fun timeStampConversionToTime(timeStamp: Long): String {
        val date = Date(timeStamp)
        @SuppressLint("SimpleDateFormat") val jdf = SimpleDateFormat("hh:mm a")
        jdf.timeZone = TimeZone.getTimeZone("Asia/Kolkata")
        return jdf.format(date)
    }

    override fun getItemCount(): Int {
        return chatArrayList.size
    }

    inner class MessageHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv_msg: TextView
        var tv_time: TextView
        var tv_seen: TextView

        init {
            tv_msg = itemView.findViewById(R.id.tv_chat_received)
            tv_time = itemView.findViewById(R.id.tv_chat_time_received)
            tv_seen = itemView.findViewById(R.id.tv_seen)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (chatArrayList[position].receiverId.equals(currentUser_sender)) {
            MSG_TYPE_LEFT_RECEIVED
        } else MSG_TYPE_RIGHT_RECEIVED
    }

    companion object {
        private const val MSG_TYPE_LEFT_RECEIVED = 0
        private const val MSG_TYPE_RIGHT_RECEIVED = 1
    }

    init {
        this.chatArrayList = chatArrayList
        this.context = context
        this.currentUser_sender = currentUser_sender
    }
}
