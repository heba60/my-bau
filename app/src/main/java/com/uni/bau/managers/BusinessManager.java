package com.uni.bau.managers;

import static com.uni.bau.helpers.ApiConstants.AllAnnouncements;
import static com.uni.bau.helpers.ApiConstants.PLAN_LINK;
import static com.uni.bau.helpers.ApiConstants.StdSemesterView;
import static com.uni.bau.helpers.ApiConstants.academiccalendar;
import static com.uni.bau.helpers.ApiConstants.reCapta;
import static com.uni.bau.helpers.ApiConstants.rmiMethod;
import static com.uni.bau.helpers.ApiConstants.rmiMethodcourses;
import static com.uni.bau.helpers.ApiConstants.uniNews;
import static com.uni.bau.helpers.ApiConstants.uniNews1;
import static com.uni.bau.helpers.ApiConstants.uniNews2;

import android.content.Context;

import com.google.gson.Gson;
import com.uni.bau.helpers.ApiConstants;
import com.uni.bau.models.Education;
import com.uni.bau.models.GetAnnouncementsModel;
import com.uni.bau.models.MarksApiModel;
import com.uni.bau.models.NewsLitterModel;
import com.uni.bau.models.StudentClassificationsModel;
import com.uni.bau.models.StudentData;
import com.uni.bau.models.UniLoginModel;
import com.uni.bau.utilities.MyClient;
import com.uni.bau.utilities.Utils;

import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;


public class BusinessManager {
    String scudleUrl = "";
    public void getNewsAPI(Context context, final ApiCallResponse callResponse) {
        new Thread(() -> {

            ConnectionManager.GET(context, uniNews, new ApiCallResponse() {
                @Override
                public void onSuccess(Object responseObject, String responseMessage) {
                    try {
                        callResponse.onSuccess(responseObject, responseMessage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(String errorResponse) {
                    callResponse.onFailure(errorResponse);
                }
            });
        }).start();

    }

    public void getNewsAPIPaging(Context context, final ApiCallResponse callResponse) {
        new Thread(() -> {
            ConnectionManager.GET(context, uniNews1, new ApiCallResponse() {
                @Override
                public void onSuccess(Object responseObject, String responseMessage) {
                    try {
                        callResponse.onSuccess(responseObject, responseMessage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(String errorResponse) {
                    callResponse.onFailure(errorResponse);
                }
            });
        }).start();

    }

    public void getNewsAPIPagingAPI(Context context, String page, final ApiCallResponse callResponse) {
        new Thread(() -> {
            ConnectionManager.GET(context, uniNews2 + "" + page, new ApiCallResponse() {
                @Override
                public void onSuccess(Object responseObject, String responseMessage) {
                    try {
                        callResponse.onSuccess(responseObject, responseMessage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(String errorResponse) {
                    callResponse.onFailure(errorResponse);
                }
            });
        }).start();

    }


    public void doLogin(Context context, String userName, String password, final ApiCallResponse callResponse) {
        new Thread(() -> {
            Map<String, String> Params = new HashMap<>();
            Params.put("username", userName);
            Params.put("password", password);
            new UniConnectionManager().POSTURLUNI(context, ApiConstants.LoginURl, Params, new ApiCallResponse() {
                @Override
                public void onSuccess(Object responseObject, String responseMessage) {
                    try {
                        callResponse.onSuccess(responseObject, responseMessage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(String errorResponse) {
                    callResponse.onFailure(errorResponse);
                }
            });
        }).start();

    }

    public void getStudent(Context context, final ApiCallResponse callResponse) {
        new Thread(() -> {
            Map<String, String> Params = new HashMap<>();
            Params.put("method","getStudent");
            Params.put("paramsCount","0");
            ConnectionManager.GETParamsWithCookeis(context, rmiMethod,Params, new ApiCallResponse() {
                @Override
                public void onSuccess(Object responseObject, String responseMessage) {
                    try {
                        if (responseMessage.equals("OK")) {
                            try {
                                Gson gson = new Gson();
                                String json = responseObject.toString();
                                StudentData parseObject = gson.fromJson(json, StudentData.class);
                                callResponse.onSuccess(parseObject, responseMessage);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            callResponse.onSuccess("", responseMessage);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(String errorResponse) {
                    callResponse.onFailure(errorResponse);
                }
            });
        }).start();

    }
    public void getDegrees(Context context, final ApiCallResponse callResponse) {
        new Thread(() -> {
            Map<String, String> Params = new HashMap<>();
            Params.put("method","getDegrees");
            Params.put("paramsCount","0");
            ConnectionManager.GETParamsWithCookeis(context, rmiMethodcourses,Params, new ApiCallResponse() {
                @Override
                public void onSuccess(Object responseObject, String responseMessage) {
                    try {
                        if (responseMessage.equals("OK")) {
                            try {
                                Gson gson = new Gson();
                                String json = responseObject.toString();
                                Education[] parseObject = gson.fromJson(json, Education[].class);
                                callResponse.onSuccess(parseObject, responseMessage);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            callResponse.onSuccess("", responseMessage);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(String errorResponse) {
                    callResponse.onFailure(errorResponse);
                }
            });
        }).start();

    }

    public void getColleges(Context context, final ApiCallResponse callResponse) {
        new Thread(() -> {
            Map<String, String> Params = new HashMap<>();
            Params.put("method","getColleges");
            Params.put("paramsCount","0");
            ConnectionManager.GETParamsWithCookeis(context, rmiMethodcourses,Params, new ApiCallResponse() {
                @Override
                public void onSuccess(Object responseObject, String responseMessage) {
                    try {
                        if (responseMessage.equals("OK")) {
                            try {
                                Gson gson = new Gson();
                                String json = responseObject.toString();
                                Education[] parseObject = gson.fromJson(json, Education[].class);
                                callResponse.onSuccess(parseObject, responseMessage);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            callResponse.onSuccess("", responseMessage);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(String errorResponse) {
                    callResponse.onFailure(errorResponse);
                }
            });
        }).start();

    }

    public void getDeps(Context context, String param0,final ApiCallResponse callResponse) {
        new Thread(() -> {
            Map<String, String> Params = new HashMap<>();
            Params.put("method","getDepartments");
            Params.put("paramsCount","1");
            Params.put("param0",param0);
            ConnectionManager.GETParamsWithCookeis(context, rmiMethodcourses,Params, new ApiCallResponse() {
                @Override
                public void onSuccess(Object responseObject, String responseMessage) {
                    try {
                        if (responseMessage.equals("OK")) {
                            try {
                                Gson gson = new Gson();
                                String json = responseObject.toString();
                                Education[] parseObject = gson.fromJson(json, Education[].class);
                                callResponse.onSuccess(parseObject, responseMessage);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            callResponse.onSuccess("", responseMessage);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(String errorResponse) {
                    callResponse.onFailure(errorResponse);
                }
            });
        }).start();

    }

    public void getCourses(Context context, String param0, String param1, String param2,final ApiCallResponse callResponse) {
        new Thread(() -> {
            Map<String, String> Params = new HashMap<>();
            Params.put("method","getCourses");
            Params.put("paramsCount","4");
            Params.put("param0",param0);
            Params.put("param1",param1);
            Params.put("param2",param2);
            Params.put("param3","1");

            ConnectionManager.GETParamsWithCookeis(context, rmiMethodcourses,Params, new ApiCallResponse() {
                @Override
                public void onSuccess(Object responseObject, String responseMessage) {
                    try {
                        if (responseMessage.equals("OK")) {
                            try {
                                Gson gson = new Gson();
                                String json = responseObject.toString();
                                NewsLitterModel[] parseObject = gson.fromJson(json, NewsLitterModel[].class);
                                callResponse.onSuccess(parseObject, responseMessage);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            callResponse.onSuccess("", responseMessage);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(String errorResponse) {
                    callResponse.onFailure(errorResponse);
                }
            });
        }).start();

    }

    public void getCurrentPage(Context context, final ApiCallResponse callResponse) {
        new Thread(() -> {
            Map<String, String> Params = new HashMap<>();
            Params.put("method","getCurrentPage");
            Params.put("paramsCount","0");
            ConnectionManager.GETParamsWithCookeis(context, rmiMethod,Params, new ApiCallResponse() {
                @Override
                public void onSuccess(Object responseObject, String responseMessage) {
                    try {
                        if (responseMessage.equals("OK")) {
                            try {

                                callResponse.onSuccess(null, responseMessage);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            callResponse.onSuccess("", responseMessage);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(String errorResponse) {
                    callResponse.onFailure(errorResponse);
                }
            });
        }).start();

    }

    public void getMarks(Context context, final ApiCallResponse callResponse) {
        new Thread(() -> {
            Map<String, String> Params = new HashMap<>();
            Params.put("method","getStudentSemesters");
            Params.put("paramsCount","0");
            ConnectionManager.GETParamsWithCookeis(context, rmiMethod,Params, new ApiCallResponse() {
                @Override
                public void onSuccess(Object responseObject, String responseMessage) {
                    try {
                        if (responseMessage.equals("OK")) {
                            try {
                                Gson gson = new Gson();
                                String json = responseObject.toString();
                                MarksApiModel[] parseObject = gson.fromJson(json, MarksApiModel[].class);
                                callResponse.onSuccess(parseObject, responseMessage);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            callResponse.onSuccess("", responseMessage);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(String errorResponse) {
                    callResponse.onFailure(errorResponse);
                }
            });
        }).start();

    }
    public void getStudentClassifications(Context context, final ApiCallResponse callResponse) {
        new Thread(() -> {
            Map<String, String> Params = new HashMap<>();
            Params.put("method","getStudentClassifications");
            Params.put("paramsCount","0");
            ConnectionManager.GETParamsWithCookeis(context, rmiMethod,Params, new ApiCallResponse() {
                @Override
                public void onSuccess(Object responseObject, String responseMessage) {
                    try {
                        if (responseMessage.equals("OK")) {
                            try {
                                Gson gson = new Gson();
                                String json = responseObject.toString();
                                StudentClassificationsModel[] parseObject = gson.fromJson(json, StudentClassificationsModel[].class);
                                callResponse.onSuccess(parseObject, responseMessage);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            callResponse.onSuccess("", responseMessage);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(String errorResponse) {
                    callResponse.onFailure(errorResponse);
                }
            });
        }).start();

    }

    public void getAnnouncements(Context context, final ApiCallResponse callResponse) {
        new Thread(() -> {
            Map<String, String> Params = new HashMap<>();
            Params.put("method","getAnnouncements");
            Params.put("paramsCount","0");
            ConnectionManager.GETParamsWithCookeis(context, rmiMethod,Params, new ApiCallResponse() {
                @Override
                public void onSuccess(Object responseObject, String responseMessage) {
                    try {
                        try {
                            Gson gson = new Gson();
                            String json = responseObject.toString();
                            GetAnnouncementsModel[] parseObject = gson.fromJson(json, GetAnnouncementsModel[].class);
                            callResponse.onSuccess(parseObject, responseMessage);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(String errorResponse) {
                    callResponse.onFailure(errorResponse);
                }
            });
        }).start();
    }
    public void getAnouncmentAPI(Context context, final ApiCallResponse callResponse) {
        new Thread(() -> {

            ConnectionManager.GET(context, AllAnnouncements, new ApiCallResponse() {
                @Override
                public void onSuccess(Object responseObject, String responseMessage) {
                    try {
                        callResponse.onSuccess(responseObject, responseMessage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(String errorResponse) {
                    callResponse.onFailure(errorResponse);
                }
            });
        }).start();

    }

    public void captchaAPI(Context context, final ApiCallResponse callResponse) {
        new Thread(() -> {
            Api.getInstance().get(reCapta, callResponse);

        }).start();

    }


    public void loginNow(Context context, String userName, String password, String secureVar, ApiCallResponse connectionCallback) {
        new Thread(() -> {

            try {
                RequestBody params = new FormBody.Builder()
                        .add("username", userName)
                        .add("password", password)
                        .build();
                Api.getInstance().post(ApiConstants.LoginURl, params, connectionCallback);
            } catch (Exception e) {

            }


        }).start();

    }

    public void getNewsDetailsAPI(Context context, String url, final ApiCallResponse callResponse) {
        new Thread(() -> {
            ConnectionManager.GET(context, url, new ApiCallResponse() {
                @Override
                public void onSuccess(Object responseObject, String responseMessage) {
                    try {
                        callResponse.onSuccess(responseObject, responseMessage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(String errorResponse) {
                    callResponse.onFailure(errorResponse);
                }
            });
        }).start();

    }

    public void getSchedule(Context context, final ApiCallResponse callResponse) {

        if (new Utils().getUserData(context).isDr()) {
            scudleUrl = "http://edugate.aau.edu.jo/faces/ui/pages/staff/index.xhtml";
            new Thread(() -> {
                Api.getInstance().get(scudleUrl, callResponse);
            }).start();
        } else {
            scudleUrl = "http://edugate.aau.edu.jo/faces/ui/pages/student/index.xhtml";
            new Thread(() -> {
                Api.getInstance().get(scudleUrl, callResponse);
            }).start();
        }



    }


    private static void schedulePost(ApiCallResponse connectionCallback, String year, String semester) {
        new Thread(() -> {

            RequestBody params = new FormBody.Builder()
                    .add("year", year)
                    .add("sem", semester)
                    .build();
            Api.getInstance().post(StdSemesterView, params, connectionCallback);

        }).start();
    }

    public void planAPICall(Context context, final ApiCallResponse callResponse) {

        new Thread(() -> {

            Api.getInstance().get(PLAN_LINK, new ApiCallResponse() {
                @Override
                public void onSuccess(Object responseObject, String responseMessage) {
                    callResponse.onSuccess(responseObject.toString(), "");
                }

                @Override
                public void onFailure(String error) {
                    callResponse.onFailure(error);
                }

            });

        }).start();

    }

    public void markAPICall(Context context, final ApiCallResponse callResponse) {

        new Thread(() -> {

            Api.getInstance().get(ApiConstants.MARKS_LINK, new ApiCallResponse() {
                @Override
                public void onSuccess(Object responseObject, String responseMessage) {
                    callResponse.onSuccess(responseObject.toString(), "");
                }

                @Override
                public void onFailure(String error) {
                    callResponse.onFailure(error);
                }

            });

        }).start();

    }

    public void getCalenderAPI(Context context, final ApiCallResponse callResponse) {
        new Thread(() -> {
            Api.getInstance().get(academiccalendar, new ApiCallResponse() {
                @Override
                public void onSuccess(Object responseObject, String responseMessage) {
                    callResponse.onSuccess(responseObject.toString(), "");
                }

                @Override
                public void onFailure(String error) {
                    callResponse.onFailure(error);
                }

            });

        }).start();

    }


}
