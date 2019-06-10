package net.lzzy.kirinfm.models.view;

import net.lzzy.kirinfm.models.Saying;

/**
 * 名人名言
 *
 * @author Administrator
 */
public class CelebritySayings {

    /**
     * result : {"famous_name":"闻一多","famous_saying":"个人之于社会等于身体的细胞，要一个人身体健全，不用说必须每个细胞都健全。"}
     * error_code : 0
     * reason : Succes
     */

    private Saying result;
    private int error_code;
    private String reason;

    public Saying getDictum() {
        return result;
    }

    public void setDictum(Saying dictum) {
        this.result = dictum;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

}
