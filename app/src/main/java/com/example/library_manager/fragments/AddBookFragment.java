package com.example.library_manager.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import com.example.library_manager.APIs.GoogleBookAPI;
import com.example.library_manager.Converters.ImageConverter;
import com.example.library_manager.DataBases.DataBase;
import com.example.library_manager.DatacClass.Book;
import com.example.library_manager.R;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class AddBookFragment extends Fragment {
    TextInputEditText SN,BN;
    ImageView iv;
    Bitmap b_img;

    private static final String ARG_PARAM1 = "param1";

    public AddBookFragment() {
        // Required empty public constructor
    }

    public static AddBookFragment newInstance(String param1) {
        AddBookFragment fragment = new AddBookFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        b_img= BitmapFactory.decodeResource(this.getResources(),R.drawable.book);
        // Inflate the layout for this fragment
        DataBase db=new DataBase(requireContext());
        View v= inflater.inflate(R.layout.fragment_add_book, container, false);
        TextInputLayout textInputLayout=v.findViewById(R.id.textInputLayout);
        TextInputLayout textInputLayout2=v.findViewById(R.id.textInputLayout3);
        SN=v.findViewById(R.id.sn_et);
        BN=v.findViewById(R.id.book_name_et);
        TextInputEditText BC=v.findViewById(R.id.book_copies_et);
        TextInputEditText AN=v.findViewById(R.id.author_name_et);

        Button ADD_BTN=v.findViewById(R.id.add_bt);
        iv=v.findViewById(R.id.book_img);

        iv.setOnClickListener(l-> {
            arl.launch(new Intent(MediaStore.ACTION_IMAGE_CAPTURE));
            iv.setImageBitmap(b_img);
        });

        textInputLayout.setEndIconOnClickListener(v1 -> new GoogleBookAPI(BN,AN).execute(SN.getText().toString()));

        textInputLayout.setStartIconOnClickListener(v1 -> scancode());

        textInputLayout2.setStartIconOnClickListener(v1 -> {
            arl.launch(new Intent(MediaStore.ACTION_IMAGE_CAPTURE));
            recognizeText(InputImage.fromBitmap(b_img, 0));
        });
        ADD_BTN.setOnClickListener(view -> {
            Book NB = new Book();
            NB.setBOOK_IMAGE(ImageConverter.bitmapToByte(b_img));
            NB.setBOOK_SN(SN.getText().toString());
            NB.setBOOK_NAME(BN.getText().toString());
            NB.setAUTHOR_NAME(AN.getText().toString());
            NB.setBOOK_COPIES(Integer.parseInt(0+BC.getText().toString()));
            if(NB.IsEmpty()) {
                Snackbar.make(requireView(), "Please Fill All Fields", Snackbar.LENGTH_LONG).show();
            }else {
                Snackbar.make(requireView(), "Successfully Added", Snackbar.LENGTH_LONG).show();
                db.InsertBook(NB);
               }
        });
        return v;
    }


    private void scancode() {
        ScanOptions options = new ScanOptions();
        options.setBeepEnabled(true);
        options.setPrompt(getString(R.string.scanmessage));
        options.setOrientationLocked(false);
        RESULT.launch(options);
    }

    ActivityResultLauncher<Intent> arl=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        b_img = (Bitmap) result.getData().getExtras().get("data");
        iv.setImageBitmap(b_img);
    });

    ActivityResultLauncher<ScanOptions> RESULT = registerForActivityResult(new ScanContract(), result -> {
        if (result.getContents() != null) {
            Toast.makeText(getContext(), (R.string.scantoast_s), Toast.LENGTH_SHORT).show();
            SN.setText(result.getContents());
        } else
            Toast.makeText(getContext(), (R.string.scantoast_f), Toast.LENGTH_SHORT).show();
    });

    private void recognizeText(InputImage image) {
        TextRecognizer recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
        Task<Text> result = recognizer.process(image).addOnSuccessListener(visionText -> {
            for (Text.TextBlock block : visionText.getTextBlocks()) {
                assert false;
                BN.setText(block.getText());
            }
        }).addOnFailureListener(e -> Toast.makeText(requireContext(), "Failed", Toast.LENGTH_SHORT).show());
    }
}