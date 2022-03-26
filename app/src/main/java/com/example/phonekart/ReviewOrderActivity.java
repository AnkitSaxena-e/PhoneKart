package com.example.phonekart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.JsonObjectRequest;
import com.example.phonekart.Adapter.MySingleton;
import com.example.phonekart.Modal.Product;
import com.example.phonekart.Prevalant.OrderPrevalent;
import com.example.phonekart.Prevalant.Prevalant;
import com.example.phonekart.View_Holder.catagorySearchVewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;

public class ReviewOrderActivity extends AppCompatActivity {
    
    private RecyclerView SeeOrder;
    private RecyclerView.LayoutManager llll;
    private TextView RName, RAddress, RPin, RNumber;
    private RelativeLayout COD, Online;
    private Button PlaceOr;
    private CheckBox CODCheck, OnlineCheck;
    private String PPPPPP = "", COnl = "COD", UUUU, NNNN;
    private final int UPI_PAYMENT = 0;
    private Dialog LoadingBar;
    private String Keyword, CheckC = "No";
    private int k = 0;
    private int j = 5;
    private int h = 0;
    private int m = 0;
    private ImageView back;
    private int n = 0;
    private int f = 0;
    private int d = 0;
    private int a = 0;
    private int b = 0;


    final private String FCM_API = "https://fcm.googleapis.com/fcm/send";
    final private String serverKey = "key=" + "AAAA-Q_OEbU:APA91bFHe5HputYzQS0qvEVJJsXrLFo_KAyiITLPADrRUPmZeKDxhK0evOTYOkFoRcuqrf_WaEGHLYflYyasv3CmZZu2wZM7Brp2w-hk4LsgFmum1A3wFK4EPjlWnWlMET2E91yW_g8y";

    final private String contentType = "application/json";
    final String TAG = "NOTIFICATION TAG";

    String NOTIFICATION_TITLE;
    String NOTIFICATION_MESSAGE;
    String TOPIC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_order);
        
        PPPPPP = getIntent().getStringExtra("PPPP");
        UUUU = getIntent().getStringExtra("UU");
        NNNN = getIntent().getStringExtra("PPP");
        
        SeeOrder = findViewById(R.id.ROSeeOrderUU);
        SeeOrder.setHasFixedSize(true);
        SeeOrder.setItemViewCacheSize(20);
        SeeOrder.setDrawingCacheEnabled(true);
        SeeOrder.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_AUTO);
        llll = new LinearLayoutManager(ReviewOrderActivity.this);
        SeeOrder.setLayoutManager(llll);

        back = findViewById(R.id.back_sett_rev_order);
        
        RName = findViewById(R.id.ROresourseName);
        RPin = findViewById(R.id.ROresoursePin);
        RAddress = findViewById(R.id.ROresourseAddress);
        RNumber = findViewById(R.id.ROresourseNumber);
        
        COD = findViewById(R.id.ROCODoooo);
        Online = findViewById(R.id.ROOnlinePoooo);
        
        PlaceOr = findViewById(R.id.ROPlaceOrder);
        
        CODCheck = findViewById(R.id.ROCODCheck);
        OnlineCheck = findViewById(R.id.ROOnlineCheck);

        Window window = ReviewOrderActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(android.graphics.Color.parseColor("#093145"));

        LoadingBar = new Dialog(ReviewOrderActivity.this);
        LoadingBar.setContentView(R.layout.loading_dialog);
        LoadingBar.setCancelable(false);

        COD.setOnClickListener(v -> {

            if(a == 0){

                COnl = "COD";
                PlaceOr.setText("PlaceOrder");
                PlaceOr.setEnabled(true);
                PlaceOr.setBackgroundColor(Color.parseColor("#5a98ec"));
                CODCheck.setChecked(true);
                OnlineCheck.setChecked(false);

                a = 1;

            }else{

                if(b == 1){
                    COnl = "COD";
                    PlaceOr.setText("PlaceOrder");
                    PlaceOr.setEnabled(true);
                    PlaceOr.setBackgroundColor(Color.parseColor("#5a98ec"));
                    CODCheck.setChecked(true);
                    OnlineCheck.setChecked(false);

                    a = 1;
                }else{
                    COnl = "COD";
                    PlaceOr.setText("PlaceOrder");
                    PlaceOr.setEnabled(true);
                    PlaceOr.setBackgroundColor(Color.parseColor("#5a98ec"));
                    CODCheck.setChecked(false);
                    OnlineCheck.setChecked(false);

                    a = 0;
                }
            }
        });

        Online.setOnClickListener(v -> {

            if(b == 0){

                COnl = "Online";
                PlaceOr.setText("Pay Online");
                PlaceOr.setEnabled(true);
                PlaceOr.setBackgroundColor(Color.parseColor("#5a98ec"));
                CODCheck.setChecked(false);
                OnlineCheck.setChecked(true);

                b = 1;

            }else{

                if(a == 1){
                    COnl = "Online";
                    PlaceOr.setText("Pay Online");
                    PlaceOr.setEnabled(true);
                    PlaceOr.setBackgroundColor(Color.parseColor("#5a98ec"));
                    CODCheck.setChecked(false);
                    OnlineCheck.setChecked(true);

                    b = 1;
                }else{
                    COnl = "Online";
                    PlaceOr.setText("Pay Online");
                    PlaceOr.setEnabled(true);
                    PlaceOr.setBackgroundColor(Color.parseColor("#5a90ec"));
                    CODCheck.setChecked(false);
                    OnlineCheck.setChecked(false);

                    b = 0;
                }
            }
        });

        if(!CODCheck.isChecked() && !OnlineCheck.isChecked()){
            PlaceOr.setEnabled(false);
        }

        SetAddre();
        
        if(!CODCheck.isChecked() && !OnlineCheck.isChecked()){
            PlaceOr.setClickable(false);
        }
        
        PlaceOr.setOnClickListener(v -> {

            LoadingBar.show();

            if(COnl.equals("COD")){
                ConfermOrder(PPPPPP);
            }
            else{
                OnlinePay();
            }

        });

        back.setOnClickListener(v -> {

            Intent i = new Intent(ReviewOrderActivity.this, HomeActivity.class);
            i.putExtra("eeee", "PDA");
            startActivity(i);

        });
        
    }

    private void OnlinePay() {

        String PayName = Paper.book().read(OrderPrevalent.NameP);
        String PayID = "9691267090@paytm";
        String PayAmount = Paper.book().read(OrderPrevalent.TotalPrice);
        String PayText = "Payment to STSCom";

        if (TextUtils.isEmpty(PayAmount)) {
            Toast.makeText(ReviewOrderActivity.this, "Something Went Wrong Please Try Again", Toast.LENGTH_SHORT).show();
        } else {
            payUsingUpi(PayName, PayID, PayText, PayAmount);
        }

    }

    private void payUsingUpi(String payName, String payID, String payText, String payAmount) {

        Uri uri = Uri.parse("upi://pay").buildUpon()

                .appendQueryParameter("pa", payID)
                .appendQueryParameter("pn", payName)
                .appendQueryParameter("tn", payText)
                .appendQueryParameter("am", payAmount)
                .appendQueryParameter("cu", "INR")
                .build();

        Intent upiPayIntent = new Intent(Intent.ACTION_VIEW, uri);

        Intent chooser = Intent.createChooser(upiPayIntent, "Pay with");

        if(chooser.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(chooser, UPI_PAYMENT);
        } else {
            Toast.makeText(ReviewOrderActivity.this,"No UPI app found, please install one to continue",Toast.LENGTH_SHORT).show();
        }

    }

    private void SetAddre() {
        
        RName.setText(Paper.book().read(OrderPrevalent.NameP));
        RPin.setText(Paper.book().read(OrderPrevalent.PinP));
        RAddress.setText(Paper.book().read(OrderPrevalent.AddressP));
        RNumber.setText(Paper.book().read(OrderPrevalent.NumberP));
        
    }

    @Override
    protected void onStart() {
        super.onStart();

        DatabaseReference OrREF = FirebaseDatabase.getInstance().getReference().child("Products");

        FirebaseRecyclerOptions<Product> OrOption =
                new FirebaseRecyclerOptions.Builder<Product>()
                .setQuery(OrREF.orderByChild("Pid").equalTo(PPPPPP), Product.class)
                .build();

        FirebaseRecyclerAdapter<Product, catagorySearchVewHolder> OrAdapter =
                new FirebaseRecyclerAdapter<Product, catagorySearchVewHolder>(OrOption) {
            @SuppressLint("SetTextI18n")
            @Override
            protected void onBindViewHolder(@NonNull catagorySearchVewHolder Holder, int i, @NonNull Product Layout) {

                try {

                    String n = Layout.getProductName();
                    String p = Layout.getPrice();
                    String q = Paper.book().read(OrderPrevalent.Quantity);
                    String c = Layout.getColor();
                    String sr = Layout.getSnR();
                    String im = Layout.getImage1();
                    String ke = Layout.getKeyward();

                    Keyword = ke;

                    Holder.tltProductName.setText(n);
                    Holder.tltProductPrice.setText("₹" + p);
                    Holder.tltProductQuantity.setText("Qut:" + q);
                    Holder.tltProductColor.setText(c);
                    Holder.tltProductSR.setText(sr);
                    Picasso.get().load(im).fit().centerCrop().into(Holder.tltProductImage);

                }catch (Exception e){
                    Toast.makeText(ReviewOrderActivity.this, "ggff" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                
            }

            @NonNull
            @Override
            public catagorySearchVewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_resource_file, parent, false);
                catagorySearchVewHolder holder = new catagorySearchVewHolder(view);
                return holder;
            }
        };

        SeeOrder.setAdapter(OrAdapter);
        OrAdapter.startListening();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == UPI_PAYMENT) {
            if ((RESULT_OK == resultCode) || (resultCode == 11)) {
                if (data != null) {
                    String trxt = data.getStringExtra("response");
                    ArrayList<String> dataList = new ArrayList<>();
                    dataList.add(trxt);
                    upiPaymentDataOperation(dataList);
                } else {
                    ArrayList<String> dataList = new ArrayList<>();
                    dataList.add("nothing");
                    upiPaymentDataOperation(dataList);
                }
            } else {
                //when user simply back without payment
                ArrayList<String> dataList = new ArrayList<>();
                dataList.add("nothing");
                upiPaymentDataOperation(dataList);
            }
        } else {
            throw new IllegalStateException("Unexpected value: " + requestCode);
        }
    }

    private void upiPaymentDataOperation(ArrayList<String> data) {
        if (isConnectionAvailable(ReviewOrderActivity.this)) {
            String str = data.get(0);
            String paymentCancel = "";
            if(str == null) str = "discard";
            String status = "";
            String approvalRefNo = "";
            String response[] = str.split("&");
            for (int i = 0; i < response.length; i++) {
                String equalStr[] = response[i].split("=");
                if(equalStr.length >= 2) {
                    if (equalStr[0].toLowerCase().equals("Status".toLowerCase())) {
                        status = equalStr[1].toLowerCase();
                    }
                    else if (equalStr[0].toLowerCase().equals("ApprovalRefNo".toLowerCase()) || equalStr[0].toLowerCase().equals("txnRef".toLowerCase())) {
                        approvalRefNo = equalStr[1];
                    }
                }
                else {
                    paymentCancel = "Payment cancelled by user.";
                }
            }

            if (status.equals("success")) {
                Toast.makeText(ReviewOrderActivity.this, "Transaction successful.", Toast.LENGTH_SHORT).show();

                ConfermOrder(PPPPPP);

            }
            else if("Payment cancelled by user.".equals(paymentCancel)) {
                Toast.makeText(ReviewOrderActivity.this, "Payment cancelled by user.", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(ReviewOrderActivity.this, "Transaction failed.Please try again", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(ReviewOrderActivity.this, "Internet connection is not available. Please check and try again", Toast.LENGTH_SHORT).show();
        }
    }

    private void ConfermOrder(String PP) {

        try {

            final String SaveCurruntTime, SaveCurruntDate, Time;

            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat currentDate = new SimpleDateFormat("yyyy-MM-dd");
            SaveCurruntDate = currentDate.format(calendar.getTime());

            SimpleDateFormat currentTime = new SimpleDateFormat(" hh:mm:ss a");
            SaveCurruntTime = currentTime.format(calendar.getTime());

            Time = SaveCurruntDate + SaveCurruntTime;

            String total_price = Paper.book().read(OrderPrevalent.TotalPrice);
            String name = Paper.book().read(OrderPrevalent.NameP);
            String number = Paper.book().read(OrderPrevalent.NumberP);
            String address = Paper.book().read(OrderPrevalent.AddressP);
            String city = Paper.book().read(OrderPrevalent.CityP);
            String pin = Paper.book().read(OrderPrevalent.PinP);
            String qqqqq = Paper.book().read(OrderPrevalent.Quantity);

            final DatabaseReference orderRefGG = FirebaseDatabase.getInstance().getReference().child("Order").child(Time);

            final HashMap<String, Object> ordersMap = new HashMap<>();
            ordersMap.put("TotalAmount", total_price);
            ordersMap.put("Name", name);
            ordersMap.put("PhoneNumber", number);
            ordersMap.put("Address", address);
            ordersMap.put("City", city);
            ordersMap.put("Pin", pin);
            ordersMap.put("ProductPID", PP);
            ordersMap.put("Quantity", qqqqq);
            ordersMap.put("ProductN", NNNN);
            ordersMap.put("FromUser", Prevalant.currentOnlineUser.getUserId());
            ordersMap.put("ToUser", Paper.book().read(OrderPrevalent.TUID));
            ordersMap.put("FromUserN", Prevalant.currentOnlineUser.getName());
            ordersMap.put("ToUserN", UUUU);
            ordersMap.put("Check", "No");
            ordersMap.put("KeyWord", Keyword);
            ordersMap.put("Return", "A");
            ordersMap.put("ONO", Prevalant.currentOnlineUser.getNumber());
            ordersMap.put("PID", Time);
            ordersMap.put("Date", SaveCurruntDate);
            ordersMap.put("Time", SaveCurruntTime);
            ordersMap.put("DeliveryDetail", "--/--/--");


            String To = Paper.book().read(OrderPrevalent.Token);
            String Ti = "Order Alert";
            String Me = "You Have An Order....Please Check It..";

            Toast.makeText(this, "UTD = " + To, Toast.LENGTH_SHORT).show();

            sendNotificationss(To, Ti, Me, Time, ordersMap);

             orderRefGG.updateChildren(ordersMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(ReviewOrderActivity.this, "Your Order Has Been Placed Successfully", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(ReviewOrderActivity.this, OrderPlacedActivity.class);
                                LoadingBar.dismiss();
                                startActivity(i);
                            }
                        });


        }catch (Exception e){
            Toast.makeText(ReviewOrderActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private void sendNotificationss(String to, String ti, String me, String time, HashMap<String, Object> ordersMap) {

        TOPIC = to; //topic must match with what the receiver subscribed to
        NOTIFICATION_TITLE = ti;
        NOTIFICATION_MESSAGE = me;

        JSONObject notification = new JSONObject();
        JSONObject notifcationBody = new JSONObject();
        try {
            notifcationBody.put("title", NOTIFICATION_TITLE);
            notifcationBody.put("message", NOTIFICATION_MESSAGE);

            notification.put("to", TOPIC);
            notification.put("data", notifcationBody);
        } catch (JSONException e) {
            Log.e(TAG, "onCreate: " + e.getMessage() );
        }
        sendNotification(notification);

    }

    private void sendNotification(JSONObject notification) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(FCM_API, notification,
                response -> {
                    Log.i(TAG, "onResponse: " + response.toString());

                    try {
                        Toast.makeText(ReviewOrderActivity.this, response.get("success").toString(), Toast.LENGTH_SHORT).show();

                        String jj = response.get("success").toString();

                        if(jj.equals("1")){

                            CheckC = "Yes";

                        }

                    } catch (JSONException e) {
                        Toast.makeText(ReviewOrderActivity.this, "2 " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                },
                error -> {
                    Toast.makeText(ReviewOrderActivity.this, "Request error", Toast.LENGTH_LONG).show();
                    Log.i(TAG, "onErrorResponse: Didn't work");
                }){
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", serverKey);
                params.put("Content-Type", contentType);
                return params;
            }
        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);

    }

//    public void sendNotifications(String usertoken, String title, String message, String time, HashMap<String, Object> orderRef) {
//        Data data = new Data(title, message);
//        NotificationSender sender = new NotificationSender(data, usertoken);
//
//        apiService.sendNotifcation(sender).enqueue(new Callback<MyResponse>() {
//            @Override
//            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
//
//                Toast.makeText(ReviewOrderActivity.this, "response - " + response + "\n" + "call - " + call, Toast.LENGTH_SHORT).show();
//
//                if (response.code() == 200) {
//                    if (response.body().success != 1) {
//                        Toast.makeText(ReviewOrderActivity.this, "No Internet Access! Please Try Again!", Toast.LENGTH_LONG);
//                    }else {
//
//                        final DatabaseReference orderRefGG = FirebaseDatabase.getInstance().getReference().child("Order").child(time);
//                        orderRefGG.updateChildren(orderRef).addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                Toast.makeText(ReviewOrderActivity.this, "Your Order Has Been Placed Successfully", Toast.LENGTH_LONG).show();
//                                Intent i = new Intent(ReviewOrderActivity.this, OrderPlacedActivity.class);
//                                LoadingBar.dismiss();
//                                startActivity(i);
//                            }
//                        });
//                    }
//                }else{
//                    Toast.makeText(ReviewOrderActivity.this, "No Internet Access", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<MyResponse> call, Throwable t) {
//
//            }
//        });
//    }

    public static boolean isConnectionAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            return netInfo != null && netInfo.isConnected()
                    && netInfo.isConnectedOrConnecting()
                    && netInfo.isAvailable();
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(ReviewOrderActivity.this, HomeActivity.class);
        i.putExtra("eeee", "PDA");
        startActivity(i);
    }

}