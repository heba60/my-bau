package com.uni.bau.managers;

import android.content.Context;

import com.google.gson.Gson;
import com.uni.bau.helpers.ApiConstants;
import com.uni.bau.models.AdvModel;
import com.uni.bau.models.AttachmentModel;
import com.uni.bau.models.CheckNumberModel;
import com.uni.bau.models.GetStudentLecutresModel;
import com.uni.bau.models.JobsModel;
import com.uni.bau.models.Lectures;
import com.uni.bau.models.NotificationModel;
import com.uni.bau.models.RegisterDeviceParams;
import com.uni.bau.models.ScheduleModel;
import com.uni.bau.models.SchedulePost;
import com.uni.bau.models.SponserModel;
import com.uni.bau.models.UnReadCount;
import com.uni.bau.models.UniLoginModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UniBusinessManager {

    public void userLogin(Context context, String userName,final ApiCallResponse callResponse) {
        Map<String, String> Params = new HashMap<>();
        Params.put("UserName", ApiConstants.UNI_CODE +userName);
        Params.put("Password", "Hash"+ApiConstants.UNI_CODE +userName+"@123");
        new UniConnectionManager().POST(context, ApiConstants.loginAPI, Params, new ApiCallResponse() {
            @Override
            public void onSuccess(Object responseObject, String responseMessage) {
                if (responseMessage.equals("200")) {
                    try {
                        Gson gson = new Gson();
                        String json = responseObject.toString();
                        UniLoginModel parseObject = gson.fromJson(json, UniLoginModel.class);
                        callResponse.onSuccess(parseObject, responseMessage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    callResponse.onSuccess("", responseMessage);
                }


            }

            @Override
            public void onFailure(String errorResponse) {
                callResponse.onFailure(errorResponse);
            }
        });

    }

    public void userLoginDR(Context context, String userName,final ApiCallResponse callResponse) {
        Map<String, String> Params = new HashMap<>();
        Params.put("UserName", ApiConstants.UNI_CODE +userName);
        Params.put("Password", "Hash"+ApiConstants.UNI_CODE +userName+"@123");
        new UniConnectionManager().POST(context, ApiConstants.LoginDoctor, Params, new ApiCallResponse() {
            @Override
            public void onSuccess(Object responseObject, String responseMessage) {
                if (responseMessage.equals("200")) {
                    try {
                        Gson gson = new Gson();
                        String json = responseObject.toString();
                        UniLoginModel parseObject = gson.fromJson(json, UniLoginModel.class);
                        callResponse.onSuccess(parseObject, responseMessage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    callResponse.onSuccess("", responseMessage);
                }


            }

            @Override
            public void onFailure(String errorResponse) {
                callResponse.onFailure(errorResponse);
            }
        });

    }

    public void registerDoctorAPI(Context context, String FullName,String PhoneNumber,String user,final ApiCallResponse callResponse) {
        Map<String, String> Params = new HashMap<>();
        Params.put("FullName", FullName);
        Params.put("PhoneNumber",PhoneNumber );
        Params.put("Email",ApiConstants.UNI_CODE+""+user+"@HashStudents.com" );
        Params.put("UniversityId",ApiConstants.UNI_CODE );
        Params.put("UserName",user);
        new UniConnectionManager().POST(context, ApiConstants.registerDoctorAPI, Params, new ApiCallResponse() {
            @Override
            public void onSuccess(Object responseObject, String responseMessage) {
                if (responseMessage.equals("200")) {
                    try {
                        Gson gson = new Gson();
                        String json = responseObject.toString();
                        UniLoginModel parseObject = gson.fromJson(json, UniLoginModel.class);
                        callResponse.onSuccess(parseObject, responseMessage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    callResponse.onSuccess("", responseMessage);
                }


            }

            @Override
            public void onFailure(String errorResponse) {
                callResponse.onFailure(errorResponse);
            }
        });

    }

    public void getAttachemnt(Context context, String LectureId, String Type, final ApiCallResponse callResponse)
    {
        Map<String, String> Params = new HashMap<>();
        Params.put("LectureId",LectureId+"");
        Params.put("Type",Type+"");
        new UniConnectionManager().GET(context, ApiConstants.GetAttachments,Params, new ApiCallResponse() {
            @Override
            public void onSuccess(Object responseObject, String responseMessage) {
                if (responseMessage.equals("200")) {
                    try {
                        Gson gson = new Gson();
                        String json = responseObject.toString();
                        AttachmentModel parseObject = gson.fromJson(json, AttachmentModel.class);
                        callResponse.onSuccess(parseObject, responseMessage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    callResponse.onSuccess("", responseMessage);
                }
            }

            @Override
            public void onFailure(String errorResponse) {
                callResponse.onFailure("");
            }
        });
    }

    public void getUnReadCount(Context context, final ApiCallResponse callResponse) {
        Map<String, String> Params = new HashMap<>();
        new UniConnectionManager().GET(context, ApiConstants.unReadCount, Params, new ApiCallResponse() {
            @Override
            public void onSuccess(Object responseObject, String responseMessage) {
                if (responseMessage.equals("200")) {
                    try {
                        Gson gson = new Gson();
                        String json = responseObject.toString();
                        UnReadCount parseObject = gson.fromJson(json, UnReadCount.class);
                        callResponse.onSuccess(parseObject, responseMessage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    callResponse.onSuccess("", responseMessage);
                }


            }

            @Override
            public void onFailure(String errorResponse) {
                callResponse.onFailure(errorResponse);
            }
        });

    }

    public void userRegistraion(Context context, String FullName,String PhoneNumber ,String Email ,String Specialty ,String College ,String LinkedinAccount ,String StudentNumber ,String Image , final ApiCallResponse callResponse) {
        Map<String, String> Params = new HashMap<>();
        Params.put("FullName", FullName);
        Params.put("PhoneNumber", PhoneNumber);
        Params.put("Email", Email);
        Params.put("Specialty", Specialty);
        Params.put("College", College);
        Params.put("LinkedinAccount", LinkedinAccount);
        Params.put("StudentNumber", StudentNumber);
        Params.put("UniversityId", ApiConstants.UNI_CODE);
        Params.put("Image", Image);
        new UniConnectionManager().POST(context, ApiConstants.registerAPI, Params, new ApiCallResponse() {
            @Override
            public void onSuccess(Object responseObject, String responseMessage) {
                if (responseMessage.equals("200")) {
                    try {
                        Gson gson = new Gson();
                        String json = responseObject.toString();
                        UniLoginModel parseObject = gson.fromJson(json, UniLoginModel.class);
                        callResponse.onSuccess(parseObject, responseMessage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    callResponse.onSuccess("", responseMessage);
                }


            }

            @Override
            public void onFailure(String errorResponse) {
                callResponse.onFailure(errorResponse);
            }
        });

    }

    public void userUpdateProfile(Context context, String FullName,String PhoneNumber ,String Email ,String Specialty ,String College ,String LinkedinAccount ,String Image , final ApiCallResponse callResponse) {
        Map<String, String> Params = new HashMap<>();
        Params.put("FullName", FullName);
        Params.put("PhoneNumber", PhoneNumber);
        Params.put("Email", Email);
        Params.put("Specialty", Specialty);
        Params.put("College", College);
        Params.put("LinkedinAccount", LinkedinAccount);
        Params.put("Image", Image);

        new UniConnectionManager().POST(context, ApiConstants.updateAPI, Params, new ApiCallResponse() {
            @Override
            public void onSuccess(Object responseObject, String responseMessage) {
                if (responseMessage.equals("200")) {
                    try {
                        Gson gson = new Gson();
                        String json = responseObject.toString();
                        UniLoginModel parseObject = gson.fromJson(json, UniLoginModel.class);
                        callResponse.onSuccess(parseObject, responseMessage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    callResponse.onSuccess("", responseMessage);
                }


            }

            @Override
            public void onFailure(String errorResponse) {
                callResponse.onFailure(errorResponse);
            }
        });

    }

    public void getJobs(Context context, final ApiCallResponse callResponse) {
        Map<String, String> Params = new HashMap<>();
        new UniConnectionManager().GET(context, ApiConstants.availableOpportunities, Params, new ApiCallResponse() {
            @Override
            public void onSuccess(Object responseObject, String responseMessage) {
                if (responseMessage.equals("200")) {
                    try {
                        Gson gson = new Gson();
                        String json = responseObject.toString();
                        JobsModel parseObject = gson.fromJson(json, JobsModel.class);
                        callResponse.onSuccess(parseObject, responseMessage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    callResponse.onSuccess("", responseMessage);
                }


            }

            @Override
            public void onFailure(String errorResponse) {
                callResponse.onFailure(errorResponse);
            }
        });

    }

    public void getNotification(Context context, final ApiCallResponse callResponse) {
        Map<String, String> Params = new HashMap<>();
        new UniConnectionManager().GET(context, ApiConstants.Notifications, Params, new ApiCallResponse() {
            @Override
            public void onSuccess(Object responseObject, String responseMessage) {
                if (responseMessage.equals("200")) {
                    try {
                        Gson gson = new Gson();
                        String json = responseObject.toString();
                        NotificationModel parseObject = gson.fromJson(json, NotificationModel.class);
                        callResponse.onSuccess(parseObject, responseMessage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    callResponse.onSuccess("", responseMessage);
                }


            }

            @Override
            public void onFailure(String errorResponse) {
                callResponse.onFailure(errorResponse);
            }
        });

    }


    public void getSponsors(Context context, final ApiCallResponse callResponse) {
        Map<String, String> Params = new HashMap<>();
        new UniConnectionManager().GET(context, ApiConstants.Sponsors, Params, new ApiCallResponse() {
            @Override
            public void onSuccess(Object responseObject, String responseMessage) {
                if (responseMessage.equals("200")) {
                    try {
                        Gson gson = new Gson();
                        String json = responseObject.toString();
                        SponserModel parseObject = gson.fromJson(json, SponserModel.class);
                        callResponse.onSuccess(parseObject, responseMessage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    callResponse.onSuccess("", responseMessage);
                }


            }

            @Override
            public void onFailure(String errorResponse) {
                callResponse.onFailure(errorResponse);
            }
        });

    }

    public void getAdv(Context context, final ApiCallResponse callResponse) {
        Map<String, String> Params = new HashMap<>();
        new UniConnectionManager().GET(context, ApiConstants.Advertisements, Params, new ApiCallResponse() {
            @Override
            public void onSuccess(Object responseObject, String responseMessage) {
                if (responseMessage.equals("200")) {
                    try {
                        Gson gson = new Gson();
                        String json = responseObject.toString();
                        AdvModel parseObject = gson.fromJson(json, AdvModel.class);
                        callResponse.onSuccess(parseObject, responseMessage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    callResponse.onSuccess("", responseMessage);
                }


            }

            @Override
            public void onFailure(String errorResponse) {
                callResponse.onFailure(errorResponse);
            }
        });

    }

    public void postStudentLectures(Context context, int Semester, int year, List<ScheduleModel> scheduleModelsArrayList, final ApiCallResponse callResponse)
    {
        List<Lectures> mLectures = new ArrayList<>();
        for (int counter = 0 ; counter < scheduleModelsArrayList.size() ; counter ++)
        {
            String fromTo[] = scheduleModelsArrayList.get(counter).getTime().split("-");
            Lectures lectures = new Lectures();
            lectures.setNumber(scheduleModelsArrayList.get(counter).getLecNum());
            lectures.setName(scheduleModelsArrayList.get(counter).getLecName());
            lectures.setSectionNumber(scheduleModelsArrayList.get(counter).getClassID());
            lectures.setLectureTime(scheduleModelsArrayList.get(counter).getTime());
            lectures.setDays(scheduleModelsArrayList.get(counter).getDays());
            mLectures.add(lectures);
        }
        SchedulePost schedulePost = new SchedulePost();
        schedulePost.setLectures(mLectures);
        schedulePost.setYear(year);
        schedulePost.setSemester(Semester);
        new UniConnectionManager().POSTRaw(context, ApiConstants.StudentLectures, schedulePost, new ApiCallResponse() {
            @Override
            public void onSuccess(Object responseObject, String responseMessage) {
                callResponse.onSuccess("", responseMessage);
            }

            @Override
            public void onFailure(String errorResponse) {
                callResponse.onFailure("");
            }
        });
    }
    public void registerDevice(Context context, RegisterDeviceParams registerDeviceParams, final ApiCallResponse callResponse)
    {
        new UniConnectionManager().POSTRawDevice(context, ApiConstants.RegisterDevice, registerDeviceParams, new ApiCallResponse() {
            @Override
            public void onSuccess(Object responseObject, String responseMessage) {
                callResponse.onSuccess("", responseMessage);
            }

            @Override
            public void onFailure(String errorResponse) {
                callResponse.onFailure("");
            }
        });
    }


    public void getStudentLectures(Context context, int Semester, int year, final ApiCallResponse callResponse)
    {
        Map<String, String> Params = new HashMap<>();
        Params.put("year",year+"");
        Params.put("semester",Semester+"");
        new UniConnectionManager().GET(context, ApiConstants.StudentLectures,Params, new ApiCallResponse() {
            @Override
            public void onSuccess(Object responseObject, String responseMessage) {
                if (responseMessage.equals("200")) {
                    try {
                        Gson gson = new Gson();
                        String json = responseObject.toString();
                        GetStudentLecutresModel parseObject = gson.fromJson(json, GetStudentLecutresModel.class);
                        callResponse.onSuccess(parseObject, responseMessage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    callResponse.onSuccess("", responseMessage);
                }
            }

            @Override
            public void onFailure(String errorResponse) {
                callResponse.onFailure("");
            }
        });
    }


}
