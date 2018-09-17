package kafka;

public class MessageContent {


    private String content;

    private String desc;

    public MessageContent(String content, String desc) {
        this.content = content;
        this.desc = desc;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override public String toString() {
        return "MessageContent{" + "content='" + content + '\'' + ", desc='" + desc + '\'' + '}';
    }

    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof MessageContent))
            return false;

        MessageContent that = (MessageContent) o;

        if (content != null ? !content.equals(that.content) : that.content != null)
            return false;
        return desc != null ? desc.equals(that.desc) : that.desc == null;
    }

    @Override public int hashCode() {
        int result = content != null ? content.hashCode() : 0;
        result = 31 * result + (desc != null ? desc.hashCode() : 0);
        return result;
    }
}
