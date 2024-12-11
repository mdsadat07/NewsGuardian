package com.example.news_guardian;

import java.util.List;

public class GuardianResponse {
    private ResponseBody response;

    public ResponseBody getResponse() {
        return response;
    }

    public static class ResponseBody {
        private List<Article> results;

        public List<Article> getResults() {
            return results;
        }
    }

    public static class Article {
        private String webTitle;
        private String webUrl;
        private String sectionName;

        public Article(String webTitle, String webUrl, String sectionName) {
            this.webTitle = webTitle;
            this.webUrl = webUrl;
            this.sectionName = sectionName;
        }

        public String getWebTitle() {
            return webTitle;
        }

        public String getWebUrl() {
            return webUrl;
        }

        public String getSectionName() {
            return sectionName;
        }
    }
}
