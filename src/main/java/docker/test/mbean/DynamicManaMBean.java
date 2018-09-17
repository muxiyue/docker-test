package docker.test.mbean;

public class DynamicManaMBean implements IDynamicManaMBean {

    private String id;
    private String name;
    private String code;

    @Override public String getId() {
        return id;
    }

    @Override public void setId(String id) {
        this.id = id;
    }

    @Override public String getName() {
        return name;
    }

    @Override public void setName(String name) {
        this.name = name;
    }

    @Override public String getCode() {
        return code;
    }

    @Override public void setCode(String code) {
        this.code = code;
    }

    @Override public String toString() {
        return "DynamicManaMBean{" + "id='" + id + '\'' + ", name='" + name + '\'' + ", code='" + code + '\'' + '}';
    }
}
