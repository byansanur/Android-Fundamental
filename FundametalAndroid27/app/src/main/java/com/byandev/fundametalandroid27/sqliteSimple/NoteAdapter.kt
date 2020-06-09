package com.byandev.fundametalandroid27.sqliteSimple

//class NoteAdapter(private val activity: Activity) : RecyclerView.Adapter<NoteAdapter.Holder>() {
//
//    var listNotes = ArrayList<ContactsContract.CommonDataKinds.Note>()
//        set(listNotes) {
//            if (listNotes.size > 0) {
//                this.listNotes.clear()
//            }
//            this.listNotes.addAll(listNotes)
//            notifyDataSetChanged()
//        }
//
//    fun addItem(note: ContactsContract.CommonDataKinds.Note) {
//        this.listNotes.add(note)
//        notifyItemInserted(this.listNotes.size - 1)
//    }
//    fun updateItem(position: Int, note: ContactsContract.CommonDataKinds.Note) {
//        this.listNotes[position] = note
//        notifyItemChanged(position, note)
//    }
//    fun removeItem(position: Int) {
//        this.listNotes.removeAt(position)
//        notifyItemRemoved(position)
//        notifyItemRangeChanged(position, this.listNotes.size)
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
//        return Holder(view)
//    }
//
//    override fun getItemCount(): Int = this.listNotes.size
//
//    override fun onBindViewHolder(holder: Holder, position: Int) {
//        holder.bind(listNotes[position])
//    }
//
//    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        fun bind(note: ContactsContract.CommonDataKinds.Note) {
//            with(itemView){
//                tv_item_title.text = note.title
//                tv_item_date.text = note.date
//                tv_item_description.text = note.description
//                cv_item_note.setOnClickListener(
//                    CustomOnItemClickListener(
//                        adapterPosition,
//                        object :
//                            CustomOnItemClickListener.OnItemClickCallback {
//                            override fun onItemClicked(view: View, position: Int) {
//                                // Setiap aksi akan mengirimkan data dan RESULT_CODE untuk diproses pada MainActivity,
//                                // contohnya seperti ini:
//                                val intent = Intent(activity, NoteAddUpdateActivity::class.java)
//                                intent.putExtra(NoteAddUpdateActivity.EXTRA_POSITION, position)
//                                intent.putExtra(NoteAddUpdateActivity.EXTRA_NOTE, note)
//                                activity.startActivityForResult(
//                                    intent,
//                                    NoteAddUpdateActivity.REQUEST_UPDATE
//                                )
//                            }
//                        })
//                )
//            }
//        }
//    }
//
//}