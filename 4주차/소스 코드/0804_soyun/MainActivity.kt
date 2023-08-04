package com.test.study03_gallery

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.storage.FirebaseStorage
import com.test.study03_gallery.databinding.ActivityMainBinding
import com.test.study03_gallery.databinding.ItemImageBinding
import java.io.FileNotFoundException

class MainActivity : AppCompatActivity() {
    // ViewBinding을 사용하여 XML 레이아웃과 연결할 변수 선언
     lateinit var activityMainBinding: ActivityMainBinding

    // Firebase Storage 인스턴스와 이미지 파일 이름 선언
     val storage = FirebaseStorage.getInstance()

    // 필요한 권한들을 배열로 선언
     val permissionList = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.ACCESS_MEDIA_LOCATION,
        Manifest.permission.INTERNET
    )

    // 갤러리에서 이미지 선택하는 ActivityResultLauncher 설정
     lateinit var albumLauncher: ActivityResultLauncher<Intent>
    // 선택한 이미지 Uri들을 담을 리스트 선언
     val selectedImageUris = mutableListOf<Uri>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ViewBinding을 사용하여 레이아웃 연결
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        // 권한 요청
        requestPermissions(permissionList, 0)

        // 갤러리에서 이미지 선택하는 ActivityResultLauncher 설정
        val contract1 = ActivityResultContracts.StartActivityForResult()
        albumLauncher = registerForActivityResult(contract1) { result ->
            if (result.resultCode == RESULT_OK) {
                // 선택한 이미지가 여러 개인 경우 clipData에서 Uri를 추출하여 리스트에 추가
                val selectedImages = result.data?.clipData
                if (selectedImages != null) {
                    for (i in 0 until selectedImages.itemCount) {
                        val uri = selectedImages.getItemAt(i).uri
                        selectedImageUris.add(uri)
                        // Firebase Storage에 이미지 업로드
                        uploadImageToFirebase(uri)
                    }
                } else {
                    // 선택한 이미지가 한 개인 경우 data에서 Uri를 추출하여 리스트에 추가
                    result.data?.data?.let { uri ->
                        selectedImageUris.add(uri)
                        // Firebase Storage에 이미지 업로드
                        uploadImageToFirebase(uri)
                    }
                }
                // RecyclerView 업데이트 및 표시
                activityMainBinding.recyclerView.run {
                    adapter?.notifyDataSetChanged()
                    visibility = View.VISIBLE
                }
            }
        }

        activityMainBinding.run {
            recyclerView.run {
                // GridLayoutManager를 사용하여 RecyclerView를 그리드 형태로 표시
                layoutManager = GridLayoutManager(this@MainActivity, 3)
                adapter = ImageAdapter(selectedImageUris)
            }

            // 이미지 선택 버튼 클릭 시 갤러리 호출
            button.setOnClickListener {
                val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                intent.type = "image/*"
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                albumLauncher.launch(intent)
            }

        }
    }

    // Firebase Storage에 이미지 업로드하는 함수
     fun uploadImageToFirebase(uri: Uri) {
        val fileRef = storage.reference.child("images/${System.currentTimeMillis()}.jpg")

        try {
            val inputStream = contentResolver.openInputStream(uri)//uri 읽어오는 용도
            inputStream?.let {
                fileRef.putStream(it)
                    .addOnSuccessListener {
                        Snackbar.make(activityMainBinding.root, "업로드가 완료되었습니다", Snackbar.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener { e ->
                        Snackbar.make(activityMainBinding.root, "업로드에 실패했습니다: ${e.message}", Snackbar.LENGTH_SHORT).show()
                    }
            } ?: run {
                Snackbar.make(activityMainBinding.root, "이미지를 찾을 수 없습니다", Snackbar.LENGTH_SHORT).show()
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            Snackbar.make(activityMainBinding.root, "이미지 파일을 찾을 수 없습니다", Snackbar.LENGTH_SHORT).show()
        }
    }


    inner class ImageAdapter( val imageUris: List<Uri>) :
        RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

        // ViewHolder 클래스 정의
        inner class ImageViewHolder( val binding: ItemImageBinding) :
            RecyclerView.ViewHolder(binding.root) {

            // ViewHolder에 데이터 바인딩하는 함수
            fun bind(uri: Uri) {
                // Glide 라이브러리를 사용하여 ImageView에 이미지 로딩
                Glide.with(binding.imageView)//이미지 로딩을 시작하는 지점을 지정
                    .load(uri)//이미지를 로딩할 대상(uri 객체)을 지정
                    .into(binding.imageView)//로딩된 이미지를 어디에 표시할지를 지정
            }
        }

        // onCreateViewHolder 메서드를 오버라이드하여 ViewHolder 생성
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
            // ItemImageBinding을 사용하여 레이아웃 인플레이션 후 ViewHolder 생성
            val binding = ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ImageViewHolder(binding)
        }

        // onBindViewHolder 메서드를 오버라이드하여 데이터와 ViewHolder를 바인딩
        override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
            val uri = imageUris[position]
            holder.bind(uri)
        }

        // getItemCount 메서드를 오버라이드하여 아이템 개수 반환
        override fun getItemCount(): Int {
            return imageUris.size
        }
    }
}
