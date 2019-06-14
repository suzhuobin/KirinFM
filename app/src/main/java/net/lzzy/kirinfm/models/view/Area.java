package net.lzzy.kirinfm.models.view;

import net.lzzy.kirinfm.models.Region;

import java.util.List;


/**
 * 地区
 *
 * @author Administrator
 */
public class Area {
    /**
     * Success : ok
     * Data : [{"id":3,"title":"北京"},{"id":5,"title":"天津"},{"id":7,"title":"河北"},{"id":83,"title":"上海"},{"id":19,"title":"山西"},{"id":31,"title":"内蒙古"},{"id":44,"title":"辽宁"},{"id":59,"title":"吉林"},{"id":69,"title":"黑龙江"},{"id":85,"title":"江苏"},{"id":99,"title":"浙江"},{"id":111,"title":"安徽"},{"id":129,"title":"福建"},{"id":139,"title":"江西"},{"id":151,"title":"山东"},{"id":169,"title":"河南"},{"id":187,"title":"湖北"},{"id":202,"title":"湖南"},{"id":217,"title":"广东"},{"id":239,"title":"广西"},{"id":254,"title":"海南"},{"id":257,"title":"重庆"},{"id":259,"title":"四川"},{"id":281,"title":"贵州"},{"id":291,"title":"云南"},{"id":316,"title":"陕西"},{"id":327,"title":"甘肃"},{"id":351,"title":"宁夏"},{"id":357,"title":"新疆"},{"id":308,"title":"西藏"},{"id":342,"title":"青海"}]
     */
    private String Success;
    private List<Region> Data;

    public String getSuccess() {
        return Success;
    }

    public void setSuccess(String Success) {
        this.Success = Success;
    }

    public List<Region> getData() {
        return Data;
    }

    public void setData(List<Region> Data) {
        this.Data = Data;
    }
}
