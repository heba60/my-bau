package com.uni.bau.utilities;

import com.uni.bau.models.AnouncementModel;
import com.uni.bau.models.ScheduleModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class ParsingUtils {

    public  List<AnouncementModel> getNews(String response) {
        List<AnouncementModel> list = new ArrayList<>();
        Document html = Jsoup.parse(response);
        Elements elements = html.getElementsByClass("cr-container").get(0).child(0).children();
//        Elements children = element.children();
        for (Element element: elements){
            try {
                Document doc = Jsoup.parse(element.toString());
                Element imgElement = doc.select("img.cr-img").first();
                String imgSrc = imgElement.attr("src");
                Document docs = Jsoup.parse(element.toString());
                Element spanElement = docs.select("span.shadowtitle").first();
                String headline = spanElement.text();
                AnouncementModel anouncementModel = new AnouncementModel();
                anouncementModel.setTitle(headline);
                anouncementModel.setUrl("https://www.bau.edu.jo/BauLivePortal/"+docs.select("a").first().attr("href"));
                anouncementModel.setImg("https://www.bau.edu.jo/BauLivePortal/"+imgSrc);
                list.add(anouncementModel);
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
        return list;
    }

    public static List<AnouncementModel> getAnnouncement(String response) {
        List<AnouncementModel> list = new ArrayList<>();
        Document html = Jsoup.parse(response);
        Elements element = html.getElementsByClass("view-content").get(0).children();
        for (Element child: element){
            AnouncementModel anouncementModel = new AnouncementModel();
            anouncementModel.setTitle(child.child(0).text());
            anouncementModel.setUrl("https://aau.edu.jo/" + child.child(0).child(0).child(0).attr("href"));
            anouncementModel.setImg("https://www.test.com");
            list.add(anouncementModel);
        }
        return list;
    }

    public static List<ScheduleModel> getSchedule(Document html) {
        List<ScheduleModel> list = new ArrayList<>();
        Elements elements = html.getElementById("contents:schedules_data").children();
        if (elements == null || elements.size() == 1){
            return list;
        }
        for (Element element: elements ){

            ScheduleModel scheduleModel = new ScheduleModel();
            scheduleModel.setLecNum(element.child(0).text());
            scheduleModel.setLecName(element.child(1).text());
            scheduleModel.setClassID(element.child(2).text());
            String allField = element.child(5).text();
            if (allField.contains("] [")){
                String[] printStr = allField.split("] \\[");
                for (String lectInfo: printStr){
                    String[] lectWellFormated = lectInfo.replaceAll("\\[", "").replaceAll("]", "").split(" ");
                    scheduleModel.setHours(lectWellFormated[1]);
                    scheduleModel.setTime(lectWellFormated[1]);
                    scheduleModel.setDays(lectWellFormated[0]);
                    scheduleModel.setHall(lectWellFormated[2]);
                }
            }else {
                String[] lectWellFormated = allField.replaceAll("\\[", "").replaceAll("]", "").split(" ");
                scheduleModel.setHours(lectWellFormated[1]);
                scheduleModel.setTime(lectWellFormated[1]);
                if (lectWellFormated.length == 4)
                {
                    scheduleModel.setDays(lectWellFormated[3].toString());
                }else if (lectWellFormated.length == 5){
                    scheduleModel.setDays(lectWellFormated[3].toString() + " " +lectWellFormated[4].toString());
                }

//                scheduleModel.setDays("");
                scheduleModel.setHall(element.child(4).text());
            }
            scheduleModel.setInstructor("هيئة تدريسية");
            list.add(scheduleModel);
        }


        return list;
    }

}
