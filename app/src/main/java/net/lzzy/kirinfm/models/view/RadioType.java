package net.lzzy.kirinfm.models.view;

import net.lzzy.kirinfm.models.RadioCategory;

import java.util.List;

/**
 * @author Administrator
 */
public class RadioType {

    /**
     * Success : ok
     * Data : [{"id":433,"title":"资讯台"},{"id":442,"title":"音乐台"},{"id":429,"title":"交通台"},{"id":439,"title":"经济台"},{"id":432,"title":"文艺台"},{"id":441,"title":"都市台"},{"id":430,"title":"体育台"},{"id":431,"title":"双语台"},{"id":440,"title":"综合台"},{"id":438,"title":"生活台"},{"id":435,"title":"旅游台"},{"id":436,"title":"曲艺台"},{"id":434,"title":"方言台"}]
     */
    private String Success;
    private List<RadioCategory> Data;

    public String getSuccess() {
        return Success;
    }

    public void setSuccess(String Success) {
        this.Success = Success;
    }

    public List<RadioCategory> getData() {
        return Data;
    }

    public void setData(List<RadioCategory> Data) {
        this.Data = Data;
    }
}
