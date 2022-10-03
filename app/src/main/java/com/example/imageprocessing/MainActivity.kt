package com.example.imageprocessing

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.imageprocessing.ui.theme.ImageProcessingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ImageProcessingTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    PickImageFromGallery()
                }
            }
        }
    }
}

@Composable
fun PickImageFromGallery() {
    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null)}
    var bitmap by remember { mutableStateOf<Bitmap?>(null)}
    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) {
        imageUri = it
    }
    var imageUri2 by remember { mutableStateOf<Uri?>(null)}
    var bitmap2 by remember { mutableStateOf<Bitmap?>(null)}
    val launcher2 = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) {
        imageUri2 = it
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row() {
          Column() {
              Button(onClick = {
                  launcher.launch("image/*")
              }) {
                  Text("Upload Image")
              }
              imageUri?.let {
                  bitmap = if(Build.VERSION.SDK_INT < 28) {
                      MediaStore.Images.Media.getBitmap(context.contentResolver, it)
                  } else {
                      val source = ImageDecoder.createSource(context.contentResolver, it)
                      ImageDecoder.decodeBitmap(source, ImageDecoder.OnHeaderDecodedListener { decoder, info, source ->
                          decoder.allocator = ImageDecoder.ALLOCATOR_SOFTWARE
                          decoder.isMutableRequired = true
                      })
                  }
//                  val width = bitmap?.getWidth();
//                  val height = bitmap?.getHeight();
//
//                  val pixels = IntArray(width?.times(height!!) ?: 0)
//                  Log.d("bitmap", "This is bitmap image tag width: ${bitmap?.getWidth()}")
//                  Log.d("bitmap", "This is bitmap image tag height: ${bitmap?.getHeight()}")
//                  Log.d("bitmap", "This is bitmap image tag pixels array: ${pixels}")
//                  Log.d("bitmap", "This is bitmap of image pixel: ${bitmap?.getPixels(pixels,0, bitmap!!.width,0, 0, width!!, height!!).toString()}")
//                  Log.d("bitmap", "This is final pixel array: ${pixels.size}")
//                  var pixel = pixels.size
//                  while(pixel > 0) {
//                      pixels[pixel] = 0
//                  }
//                  bitmap?.setPixels(pixels, 0, bitmap!!.width, 0, 0, width!!, height!!)

                  Image(bitmap = bitmap?.asImageBitmap()!!, contentDescription = "", modifier = Modifier.size(200.dp))

              }

          }
            Spacer(modifier = Modifier.width(20.dp))
            Column() {
                Button(onClick = {
                    launcher2.launch("image/*")
                }) {
                    Text("Upload Image")
                }
                imageUri2?.let {
                    bitmap2 = if(Build.VERSION.SDK_INT < 28) {
                        MediaStore.Images.Media.getBitmap(context.contentResolver, it)
                    } else {
                        val source2 = ImageDecoder.createSource(context.contentResolver, it)
                        ImageDecoder.decodeBitmap(source2, ImageDecoder.OnHeaderDecodedListener { decoder, info, source ->
                            decoder.allocator = ImageDecoder.ALLOCATOR_SOFTWARE
                            decoder.isMutableRequired = true
                        })
                    }
//                    val width = bitmap?.getWidth();
//                    val height = bitmap?.getHeight();
//
//                    val pixels = IntArray(width?.times(height!!) ?: 0)
//                    Log.d("bitmap", "This is bitmap image tag width: ${bitmap?.getWidth()}")
//                    Log.d("bitmap", "This is bitmap image tag height: ${bitmap?.getHeight()}")
//                    Log.d("bitmap", "This is bitmap image tag pixels array: ${pixels}")
//                    Log.d("bitmap", "This is bitmap of image pixel: ${bitmap?.getPixels(pixels,0, bitmap!!.width,0, 0, width!!, height!!).toString()}")
//                    Log.d("bitmap", "This is final pixel array: ${pixels.size}")
//                    var pixel = pixels.size
//                    while(pixel > 0) {
//                        pixels[pixel] = 0
//                    }
//                    bitmap?.setPixels(pixels, 0, bitmap!!.width, 0, 0, width!!, height!!)

                    Image(bitmap = bitmap2?.asImageBitmap()!!, contentDescription = "", modifier = Modifier.size(200.dp))

                }
            }
        }
        Spacer(modifier = Modifier.height(30.dp))
        Button(onClick = {}) {
            Text(text = "Create new image")
        }

    }
}

