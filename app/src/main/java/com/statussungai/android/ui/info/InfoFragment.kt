package com.statussungai.android.ui.info

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.statussungai.android.BuildConfig
import com.statussungai.android.databinding.FragmentInfoBinding
import java.io.File
import java.io.InputStream


class InfoFragment : Fragment() {
    private var _binding: FragmentInfoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val file = File("./new_panduan_biotilik.pdf")
//        file.copyInputStreamToFile(resources.openRawResource(R.raw.panduan_biotilik))
//        binding.pdfView.initWithFile(file)
        binding.apply {
//            PdfViewer.getPdfThumbnail(requireContext(), file, ivPdf, tvPdfPage)
            btnOpen.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW).apply {
//                    setDataAndType(Uri.fromFile(file), "application/pdf")
                    setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                }
                startActivity(intent)
            }
            btnShare.setOnClickListener {
                val intent = Intent(Intent.ACTION_SEND).apply {
//                    putExtra(Intent.EXTRA_STREAM,  uriFromFile(requireContext(), file))
                    flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                    type = "application/pdf"
                }
                startActivity(Intent.createChooser(intent, "share.."))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun File.copyInputStreamToFile(inputStream: InputStream) {
        this.outputStream().use { fileOut ->
            inputStream.copyTo(fileOut)
        }
    }

    private fun uriFromFile(context: Context, file:File):Uri {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", file)
        } else {
            Uri.fromFile(file)
        }
    }
}