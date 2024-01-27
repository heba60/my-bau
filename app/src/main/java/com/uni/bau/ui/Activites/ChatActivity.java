package com.uni.bau.ui.Activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fxn.pix.Options;
import com.fxn.pix.Pix;
import com.google.gson.Gson;
import com.uni.bau.R;
import com.uni.bau.application.MyApplication;
import com.uni.bau.managers.ApiCallResponse;
import com.uni.bau.managers.ChatBusinessManager;
import com.uni.bau.models.GetChatModel;
import com.uni.bau.ui.Adapters.ChatMessageAdapter;
import com.uni.bau.utilities.ProgressUtil;
import com.uni.bau.utilities.Utils;

import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;

import droidninja.filepicker.FilePickerConst;
import pub.devrel.easypermissions.EasyPermissions;

public class ChatActivity extends AppCompatActivity {
    RecyclerView recyclerChat;
    ImageView ivSendMessage;
    EditText etMessage;
    TextView toolbarText;
    Context context;
    GetChatModel getChatModel;
    LinearLayoutManager linearLayoutManager;
    ChatMessageAdapter chatMessageAdapter;
    String LECT_ID = "";
    private int PICK_PDF_REQUEST = 1;
    ImageView chatAttacment;
    ProgressBar loadMore;
    int page = 1;
    boolean loading = true;
    int visibleItemCount = 0;
    int totalItemCount;
    int pastVisiblesItems;
    ImageView attachemntChat;
    ImageView fileAttac;
    ArrayList<GetChatModel.Datum> items;
    private ArrayList<Uri> docPaths = new ArrayList<>();
    String imgFile = "";
    String imgExtention = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.updateLanguage(this);
        setContentView(R.layout.activity_chat);
        context = this;
        recyclerChat = findViewById(R.id.recyclerChat);
        loadMore = findViewById(R.id.loadMore);
        attachemntChat = findViewById(R.id.attachemntChat);
        fileAttac = findViewById(R.id.fileAttac);
        ivSendMessage = findViewById(R.id.ivSendMessage);
        etMessage = findViewById(R.id.etMessage);
        toolbarText = findViewById(R.id.toolbarText);
        chatAttacment = findViewById(R.id.chatAttacment);
        items = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerChat.setLayoutManager(linearLayoutManager);
        findViewById(R.id.ivBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        try {
            toolbarText.setText(getIntent().getStringExtra("LECT_NAME"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            LECT_ID = getIntent().getStringExtra("LECT_ID");
        } catch (Exception e) {
            e.printStackTrace();
        }
        ivSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (new Utils().getUserData(ChatActivity.this).isDr())
                {
                    sendChatDr();
                }else {
                    sendChat();
                }

            }
        });

        chatAttacment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChatActivity.this,AttachmentActivity.class).putExtra("lecID",LECT_ID));
            }
        });
        attachemntChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Options options = Options.init()
                        .setRequestCode(900)
                        .setCount(1)
                        .setFrontfacing(false)
                        .setVideoDurationLimitinSeconds(30)
                        .setExcludeVideos(false)

                        .setScreenOrientation(Options.SCREEN_ORIENTATION_PORTRAIT);
                Pix.start(ChatActivity.this, options);
            }
        });
        fileAttac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickDocClicked();
            }
        });
        loadMore();
        getChat();
    }


    private void getChat() {
        ProgressUtil.INSTANCE.showLoading(ChatActivity.this);
        new ChatBusinessManager().getChatAPI(ChatActivity.this, LECT_ID, String.valueOf(page), new ApiCallResponse() {
            @Override
            public void onSuccess(Object responseObject, String responseMessage) {
                try {
                    ProgressUtil.INSTANCE.hideLoading();
                    Gson gson = new Gson();
                    String json = responseObject.toString();
                    getChatModel = gson.fromJson(json, GetChatModel.class);
                    loading = true;
                    ArrayList<GetChatModel.Datum> temp = new ArrayList<>();
                    if (page > 1) {
                        loadMore.setVisibility(View.GONE);
                    }
                    if (page == 1) {
                        items.addAll(getChatModel.getData());
                        Collections.reverse(items);
                        chatMessageAdapter = new ChatMessageAdapter(context, items);
                        recyclerChat.setAdapter(chatMessageAdapter);
                        if (getChatModel.getData().size() > 0)
                            recyclerChat.smoothScrollToPosition(getChatModel.getData().size() - 1);
                    } else {
                        temp.addAll(items);
                        items = new ArrayList<>();
                        ArrayList<GetChatModel.Datum> temps = new ArrayList<>();
                        temps.addAll(getChatModel.getData());
                        Collections.reverse(temps);
                        items.addAll(temps);
                        items.addAll(temp);
                        chatMessageAdapter.notifiyList(items);
                    }

                } catch (Exception e) {
                    e.toString();
                }


            }

            @Override
            public void onFailure(String errorResponse) {
                errorResponse.toString();
                ProgressUtil.INSTANCE.hideLoading();
            }
        });
    }

    private void loadMore() {
        recyclerChat.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull @NotNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
            @Override
            public void onScrolled(@NonNull @NotNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy < 0) {
                    visibleItemCount = linearLayoutManager.getChildCount();
                    totalItemCount = linearLayoutManager.getItemCount();
                    pastVisiblesItems = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) <= totalItemCount) {
                            loading = false;
                            loadMore.setVisibility(View.VISIBLE);
                            page++;

                            getChat();

                        }
                    }
                }}});
    }

    public void pickDocClicked() {
        if (EasyPermissions.hasPermissions(this, FilePickerConst.PERMISSIONS_FILE_PICKER)) {
            onPickDoc();
        } else {
            EasyPermissions.requestPermissions(this, "",
                    1, FilePickerConst.PERMISSIONS_FILE_PICKER);
        }
    }

    public void onPickDoc() {
        Intent intent = new Intent();
        intent.setType("application/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select file"),800 );

    }
    private void sendChat() {
        if (etMessage.getText().toString().trim().equals("")) {
            ProgressUtil.INSTANCE.showWarningPopup(context, getString(R.string.send_empty_message));
            return;
        }
        ProgressUtil.INSTANCE.showLoading(ChatActivity.this);
        new ChatBusinessManager().sendMessageAPI(ChatActivity.this, LECT_ID, etMessage.getText().toString(), new ApiCallResponse() {
            @Override
            public void onSuccess(Object responseObject, String responseMessage) {
                try {
                    ProgressUtil.INSTANCE.hideLoading();
                    page = 1;
                    items = new ArrayList<>();
                    getChatModel = new GetChatModel();
                    loading = true;
                    getChat();
                    etMessage.setText("");
                } catch (Exception e) {
                    e.toString();
                }
            }
            @Override
            public void onFailure(String errorResponse) {
                errorResponse.toString();
                ProgressUtil.INSTANCE.hideLoading();
            }
        });
    }
    private void sendChatDr() {
        if (etMessage.getText().toString().trim().equals("")) {
            ProgressUtil.INSTANCE.showWarningPopup(context, getString(R.string.send_empty_message));
            return;
        }
        ProgressUtil.INSTANCE.showLoading(ChatActivity.this);
        new ChatBusinessManager().sendMessageAPIDR(ChatActivity.this, LECT_ID, etMessage.getText().toString(),imgFile,imgExtention, new ApiCallResponse() {
            @Override
            public void onSuccess(Object responseObject, String responseMessage) {
                try {
                    ProgressUtil.INSTANCE.hideLoading();
                    page = 1;
                    items = new ArrayList<>();
                    getChatModel = new GetChatModel();
                    loading = true;
                    getChat();
                    etMessage.setText("");
                    imgExtention = "";
                    imgFile = "";
                } catch (Exception e) {
                    e.toString();
                }
            }
            @Override
            public void onFailure(String errorResponse) {
                errorResponse.toString();
                ProgressUtil.INSTANCE.hideLoading();
            }
        });
    }

    private void sendFile (){
        new ChatBusinessManager().sendMessageAPIDR(ChatActivity.this, LECT_ID, "",imgFile,imgExtention, new ApiCallResponse() {
            @Override
            public void onSuccess(Object responseObject, String responseMessage) {
                try {
                    ProgressUtil.INSTANCE.hideLoading();
                    page = 1;
                    items = new ArrayList<>();
                    getChatModel = new GetChatModel();
                    loading = true;
                    getChat();
                    etMessage.setText("");
                    imgExtention = "";
                    imgFile = "";
                } catch (Exception e) {
                    e.toString();
                }
            }
            @Override
            public void onFailure(String errorResponse) {
                errorResponse.toString();
                ProgressUtil.INSTANCE.hideLoading();
            }
        });
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == 900) {
            ArrayList<String> returnValue = data.getStringArrayListExtra(Pix.IMAGE_RESULTS);
            imgFile = "data:image/jpeg;base64," + toBase64(returnValue.get(0));
            String extension = returnValue.get(0).substring(returnValue.get(0).lastIndexOf("."));
            imgExtention = extension.replace(".", "");
            sendFile();
        } else if (requestCode == 800) {
//            data.getData().getPath();
//            if (resultCode == Activity.RESULT_OK && data != null) {
//
//                Uri uri = data.getData();
//                String uriString = uri.toString();
//                File myFile = new File(uriString);
//                String  path = myFile.getAbsolutePath();
//
//
//                imgFile = pdfToBase64(Uri.parse(myFile.getPath().toString()));
//                String extension = docPaths.get(0).getPath().substring(docPaths.get(0).getPath().lastIndexOf("."));
//                imgExtention = extension.replace(".", "");
//            }
            Uri uri = data.getData();
            String uriString = uri.toString();
            File myFile = new File(uriString);
            String path = myFile.getAbsolutePath();
            String displayName = null;

            if (uriString.startsWith("content://")) {
                Cursor cursor = null;
                try {
                    cursor = getContentResolver().query(uri, null, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                        try {
                            imgExtention = displayName.split(".")[1];
                        } catch (Exception e) {
                            imgExtention = "pdf";
                            e.printStackTrace();
                        }

                    }
                } finally {
                    cursor.close();
                }
            } else if (uriString.startsWith("file://")) {
                displayName = myFile.getName();
                try {
                    imgExtention = displayName.split(".")[1];
                } catch (Exception e) {
                    imgExtention = "pdf";
                    e.printStackTrace();
                }

            }

            Uri   filePath = data.getData();
            File file = new File(filePath.toString());
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                InputStream in = getContentResolver().openInputStream(filePath);
                byte[] bytes = getBytes(in);
                imgFile=    Base64.encodeToString(bytes, Base64.DEFAULT);
                 Log.d("imgFile","imgFile");
            } catch (Exception e) {
                e.printStackTrace();
            }
            sendFile();
        }



//        if (requestCode == PICK_PDF_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
//
//            filePath = data.getData();
//            File file = new File(filePath.toString());
//            try {
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                InputStream in = getContentResolver().openInputStream(filePath);
//                byte[] bytes = getBytes(in);
//                Base64.encodeToString(bytes, Base64.DEFAULT);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//
//        }
    }

    public String getPDFPath(Uri uri){

        final String id = DocumentsContract.getDocumentId(uri);
        final Uri contentUri = ContentUris.withAppendedId(
                Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(contentUri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public String pdfToBase64(Uri filePath) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            InputStream in = getContentResolver().openInputStream(filePath);
            byte[] bytes = getBytes(in);
            return Base64.encodeToString(bytes, Base64.DEFAULT);
        } catch (Exception e) {
            return "";
        }

    }

    public String toBase64(String uri) {
        File file = new File(uri);  //file Path
        byte[] b = new byte[(int) file.length()];
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(b);
            for (int j = 0; j < b.length; j++) {
                System.out.print((char) b[j]);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found.");
            e.printStackTrace();
        } catch (IOException e1) {
            System.out.println("Error Reading The File.");
            e1.printStackTrace();
        }

        byte[] byteFileArray = new byte[0];
        try {
            byteFileArray = FileUtils.readFileToByteArray(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String base64String = "";
        if (byteFileArray.length > 0) {
            base64String = android.util.Base64.encodeToString(byteFileArray, android.util.Base64.NO_WRAP);
            Log.i("File Base64 string", "IMAGE PARSE ==>" + base64String);
        }

        return base64String;
    }

    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }
}