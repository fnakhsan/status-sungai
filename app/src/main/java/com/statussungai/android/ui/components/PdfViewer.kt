package com.statussungai.android.ui.components

import android.content.Context
import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.os.ParcelFileDescriptor
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import java.io.File

object PdfViewer {
    fun getPdfThumbnail(context: Context, file: File, imageView: ImageView, pageCountTV: TextView) {
        // create a new renderer
        try {
            val pfd = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)
            val renderer = PdfRenderer(pfd)
            val bitmap = Bitmap.createBitmap(900, 900, Bitmap.Config.ARGB_8888)
            val page = renderer.openPage(0)
            val count = renderer.pageCount
            pageCountTV.text = "$count pages"
            page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)

            Glide.with(context).load(bitmap).into(imageView)
            page.close()
            renderer.close()
        }
        catch (e:Exception){
            e.printStackTrace()
        }
    }

    fun openPdf() {

    }

}