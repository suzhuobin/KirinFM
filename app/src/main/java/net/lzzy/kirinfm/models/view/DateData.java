package net.lzzy.kirinfm.models.view;

import net.lzzy.kirinfm.models.PlayBill;

import java.util.List;

/**
 * @author Administrator
 */
public class DateData {

    /**
     * data : {"5":[{"id":1623214,"start_time":"00:00:00","end_time":"02:00:00","duration":7200,"res_id":4985,"day":5,"channel_id":4985,"program_id":3011853,"title":"星光不眠夜","broadcasters":[]},{"id":1623216,"start_time":"02:00:00","end_time":"03:00:00","duration":3600,"res_id":4985,"day":5,"channel_id":4985,"program_id":4118831,"title":"千山万水只为你","broadcasters":[]},{"id":1623217,"start_time":"03:00:00","end_time":"05:00:00","duration":7200,"res_id":4985,"day":5,"channel_id":4985,"program_id":10618709,"title":"车友书场","broadcasters":[]},{"id":1623218,"start_time":"05:00:00","end_time":"05:30:00","duration":1800,"res_id":4985,"day":5,"channel_id":4985,"program_id":7164867,"title":"阿龙说北京","broadcasters":[]},{"id":1623219,"start_time":"05:30:00","end_time":"06:00:00","duration":1800,"res_id":4985,"day":5,"channel_id":4985,"program_id":4118839,"title":"健康早知道","broadcasters":[]},{"id":1623220,"start_time":"06:00:00","end_time":"07:00:00","duration":3600,"res_id":4985,"day":5,"channel_id":4985,"program_id":4118845,"title":"乐活清晨","broadcasters":[]},{"id":1623221,"start_time":"07:00:00","end_time":"08:30:00","duration":5400,"res_id":4985,"day":5,"channel_id":4985,"program_id":3011838,"title":"速度早高峰","broadcasters":[]},{"id":1623222,"start_time":"08:30:00","end_time":"10:00:00","duration":5400,"res_id":4985,"day":5,"channel_id":4985,"program_id":3011839,"title":"一呼百应帮帮忙","broadcasters":[]},{"id":1623223,"start_time":"10:00:00","end_time":"11:00:00","duration":3600,"res_id":4985,"day":5,"channel_id":4985,"program_id":1914444,"title":"央广车友会","broadcasters":[]},{"id":1623224,"start_time":"11:00:00","end_time":"12:00:00","duration":3600,"res_id":4985,"day":5,"channel_id":4985,"program_id":4118861,"title":"高速加油站","broadcasters":[]},{"id":1623225,"start_time":"12:00:00","end_time":"13:00:00","duration":3600,"res_id":4985,"day":5,"channel_id":4985,"program_id":9615174,"title":"欢乐大冲关","broadcasters":[]},{"id":1623226,"start_time":"13:00:00","end_time":"14:00:00","duration":3600,"res_id":4985,"day":5,"channel_id":4985,"program_id":3011842,"title":"音乐high一点","broadcasters":[]},{"id":1623227,"start_time":"14:00:00","end_time":"15:00:00","duration":3600,"res_id":4985,"day":5,"channel_id":4985,"program_id":4118869,"title":"畅游天下","broadcasters":[]},{"id":1623229,"start_time":"15:00:00","end_time":"16:00:00","duration":3600,"res_id":4985,"day":5,"channel_id":4985,"program_id":3011843,"title":"汽车风云","broadcasters":[]},{"id":1623230,"start_time":"16:00:00","end_time":"17:00:00","duration":3600,"res_id":4985,"day":5,"channel_id":4985,"program_id":3011845,"title":"月吃越美","broadcasters":[]},{"id":1623231,"start_time":"17:00:00","end_time":"19:00:00","duration":7200,"res_id":4985,"day":5,"channel_id":4985,"program_id":7749015,"title":"下班快乐","broadcasters":[]},{"id":1623232,"start_time":"19:00:00","end_time":"20:00:00","duration":3600,"res_id":4985,"day":5,"channel_id":4985,"program_id":4118877,"title":"战马能量音乐","broadcasters":[]},{"id":1623233,"start_time":"20:00:00","end_time":"21:00:00","duration":3600,"res_id":4985,"day":5,"channel_id":4985,"program_id":6618500,"title":"岁月如歌","broadcasters":[]},{"id":1623234,"start_time":"21:00:00","end_time":"22:00:00","duration":3600,"res_id":4985,"day":5,"channel_id":4985,"program_id":3011851,"title":"北辰在找你","broadcasters":[]},{"id":1623235,"start_time":"22:00:00","end_time":"23:00:00","duration":3600,"res_id":4985,"day":5,"channel_id":4985,"program_id":3011849,"title":"我们的音乐盒子","broadcasters":[]},{"id":1623236,"start_time":"23:00:00","end_time":"23:30:00","duration":1800,"res_id":4985,"day":5,"channel_id":4985,"program_id":10618709,"title":"车友书场","broadcasters":[]},{"id":1623237,"start_time":"23:30:00","end_time":"23:59:00","duration":1740,"res_id":4985,"day":5,"channel_id":4985,"program_id":3011852,"title":"应急档案","broadcasters":[]}]}
     * errmsg :
     * errcode : 0
     */

    private DataBean data;
    private String errmsg;
    private int errcode;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public static class DataBean {
        private List<PlayBill> playBills;
        /**
         * data : {"5":[{"id":1623214,"start_time":"00:00:00","end_time":"02:00:00","duration":7200,"res_id":4985,"day":5,"channel_id":4985,"program_id":3011853,"title":"星光不眠夜","broadcasters":[]},{"id":1623216,"start_time":"02:00:00","end_time":"03:00:00","duration":3600,"res_id":4985,"day":5,"channel_id":4985,"program_id":4118831,"title":"千山万水只为你","broadcasters":[]},{"id":1623217,"start_time":"03:00:00","end_time":"05:00:00","duration":7200,"res_id":4985,"day":5,"channel_id":4985,"program_id":10618709,"title":"车友书场","broadcasters":[]},{"id":1623218,"start_time":"05:00:00","end_time":"05:30:00","duration":1800,"res_id":4985,"day":5,"channel_id":4985,"program_id":7164867,"title":"阿龙说北京","broadcasters":[]},{"id":1623219,"start_time":"05:30:00","end_time":"06:00:00","duration":1800,"res_id":4985,"day":5,"channel_id":4985,"program_id":4118839,"title":"健康早知道","broadcasters":[]},{"id":1623220,"start_time":"06:00:00","end_time":"07:00:00","duration":3600,"res_id":4985,"day":5,"channel_id":4985,"program_id":4118845,"title":"乐活清晨","broadcasters":[]},{"id":1623221,"start_time":"07:00:00","end_time":"08:30:00","duration":5400,"res_id":4985,"day":5,"channel_id":4985,"program_id":3011838,"title":"速度早高峰","broadcasters":[]},{"id":1623222,"start_time":"08:30:00","end_time":"10:00:00","duration":5400,"res_id":4985,"day":5,"channel_id":4985,"program_id":3011839,"title":"一呼百应帮帮忙","broadcasters":[]},{"id":1623223,"start_time":"10:00:00","end_time":"11:00:00","duration":3600,"res_id":4985,"day":5,"channel_id":4985,"program_id":1914444,"title":"央广车友会","broadcasters":[]},{"id":1623224,"start_time":"11:00:00","end_time":"12:00:00","duration":3600,"res_id":4985,"day":5,"channel_id":4985,"program_id":4118861,"title":"高速加油站","broadcasters":[]},{"id":1623225,"start_time":"12:00:00","end_time":"13:00:00","duration":3600,"res_id":4985,"day":5,"channel_id":4985,"program_id":9615174,"title":"欢乐大冲关","broadcasters":[]},{"id":1623226,"start_time":"13:00:00","end_time":"14:00:00","duration":3600,"res_id":4985,"day":5,"channel_id":4985,"program_id":3011842,"title":"音乐high一点","broadcasters":[]},{"id":1623227,"start_time":"14:00:00","end_time":"15:00:00","duration":3600,"res_id":4985,"day":5,"channel_id":4985,"program_id":4118869,"title":"畅游天下","broadcasters":[]},{"id":1623229,"start_time":"15:00:00","end_time":"16:00:00","duration":3600,"res_id":4985,"day":5,"channel_id":4985,"program_id":3011843,"title":"汽车风云","broadcasters":[]},{"id":1623230,"start_time":"16:00:00","end_time":"17:00:00","duration":3600,"res_id":4985,"day":5,"channel_id":4985,"program_id":3011845,"title":"月吃越美","broadcasters":[]},{"id":1623231,"start_time":"17:00:00","end_time":"19:00:00","duration":7200,"res_id":4985,"day":5,"channel_id":4985,"program_id":7749015,"title":"下班快乐","broadcasters":[]},{"id":1623232,"start_time":"19:00:00","end_time":"20:00:00","duration":3600,"res_id":4985,"day":5,"channel_id":4985,"program_id":4118877,"title":"战马能量音乐","broadcasters":[]},{"id":1623233,"start_time":"20:00:00","end_time":"21:00:00","duration":3600,"res_id":4985,"day":5,"channel_id":4985,"program_id":6618500,"title":"岁月如歌","broadcasters":[]},{"id":1623234,"start_time":"21:00:00","end_time":"22:00:00","duration":3600,"res_id":4985,"day":5,"channel_id":4985,"program_id":3011851,"title":"北辰在找你","broadcasters":[]},{"id":1623235,"start_time":"22:00:00","end_time":"23:00:00","duration":3600,"res_id":4985,"day":5,"channel_id":4985,"program_id":3011849,"title":"我们的音乐盒子","broadcasters":[]},{"id":1623236,"start_time":"23:00:00","end_time":"23:30:00","duration":1800,"res_id":4985,"day":5,"channel_id":4985,"program_id":10618709,"title":"车友书场","broadcasters":[]},{"id":1623237,"start_time":"23:30:00","end_time":"23:59:00","duration":1740,"res_id":4985,"day":5,"channel_id":4985,"program_id":3011852,"title":"应急档案","broadcasters":[]}]}
         * errmsg :
         * errcode : 0
         */

        private Datas data;
        private String errmsg;
        private int errcode;

        public List<PlayBill> getPlayBills() {
            return playBills;
        }

        public void setPlayBills(List<PlayBill> playBills) {
            this.playBills = playBills;
        }

        public Datas getData() {
            return data;
        }

        public void setData(Datas data) {
            this.data = data;
        }

        public String getErrmsg() {
            return errmsg;
        }

        public void setErrmsg(String errmsg) {
            this.errmsg = errmsg;
        }

        public int getErrcode() {
            return errcode;
        }

        public void setErrcode(int errcode) {
            this.errcode = errcode;
        }


        public static class Datas {
            private List<PlayBill> datas;

            public List<PlayBill> getDatas() {
                return datas;
            }

            public void setDatas(List<PlayBill> datas) {
                this.datas = datas;
            }

        }
    }
}
