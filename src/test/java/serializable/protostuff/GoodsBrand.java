package serializable.protostuff;


import java.io.Serializable;
import java.util.Date;

public class GoodsBrand implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -2744410266448869203L;

	public GoodsBrand() {

    }

    public GoodsBrand(Long brandId) {
        this.brandId = brandId;
    }



    private Long brandId;


    private String brandNickname;
    /*
     * 品牌名称
     */
//    @Length(min = 2, max = 16, message = "品牌名称 长度必须在 2字符 ~ 16字符之间.")
//    @Pattern(regexp = "[^''\\[\\]\\<\\>?!]+")
    private String brandName;
    /*
     * 品牌网址
     */
    // @NotNull(message = "网站格式输入不正确！")
    private String brandUrl;
    /*
     * 品牌LOGO
     */
    private String brandLogo;
    /*
     * 品牌排序
     */
    private Integer brandSort;
    /*
     * 推荐到首页
     */
    private String promIndex;
    /*
     * SEO优化标题
     */
    private String brandSeoTitle;
    /*
     * SEO优化关键字
     */
    private String brandSeoKeyword;
    /*
     * SEO优化介绍
     */
    private String brandSeoDesc;
    /*
     * 删除标记 默认0 不删除
     */
    private String brandDelflag;
    /*
     * 品牌创建人名称
     */
    private String brandCreateName;
    /*
     * 品牌创建时间
     */
    private Date brandCreateTime;
    /*
     * 品牌修改人名称
     */
    private String brandModifiedName;
    /*
     * 品牌修改时间
     */
    private Date brandModifiedTime;
    /*
     * 品牌删除人名称
     */
    private String brandDelName;
    /*
     * 品牌删除时间
     */
    private Date brandDelTime;
    /*
     * 品牌详细介绍
     */
    private String brandDesc;

    /*
     * 申请品牌Id
     */
    private Long grandBrandId;

    /*
     * 申请店铺名称
     */
    private String storeName;

    /*
     * 审核状态 0:未审核 1:审核通过 2:拒绝
     */
    private String rateStatus;
    /*
     * 商家编号
     */
    private Long thirdId;
    /*
     * 拒绝理由
     */
    private String reason;
    /*
     * 推荐到首页
     */
    private String brandPromIndex;

    public String getBrandPromIndex() {
        return brandPromIndex;
    }

    public void setBrandPromIndex(String brandPromIndex) {
        this.brandPromIndex = brandPromIndex;
    }

    public String getRateStatus() {
        return rateStatus;
    }

    public void setRateStatus(String rateStatus) {
        this.rateStatus = rateStatus;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Long getThirdId() {
        return thirdId;
    }

    public void setThirdId(Long thirdId) {
        this.thirdId = thirdId;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getBrandNickname() {
        return brandNickname;
    }

    public void setBrandNickname(String brandNickname) {
        this.brandNickname = brandNickname.trim();
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName.trim();
    }

    public String getBrandUrl() {
        return brandUrl;
    }

    public void setBrandUrl(String brandUrl) {
        if (brandUrl != null) {
            this.brandUrl = brandUrl.trim();
        }
        else {
            this.brandUrl = null;
        }

    }

    public String getBrandLogo() {
        return brandLogo;
    }

    public void setBrandLogo(String brandLogo) {
        this.brandLogo = brandLogo;
    }

    public Integer getBrandSort() {
        return brandSort;
    }

    public void setBrandSort(Integer brandSort) {
        this.brandSort = brandSort;
    }

    public String getBrandSeoTitle() {
        return brandSeoTitle;
    }

    public void setBrandSeoTitle(String brandSeoTitle) {
        this.brandSeoTitle = brandSeoTitle.trim();
    }

    public String getBrandSeoKeyword() {
        return brandSeoKeyword;
    }

    public void setBrandSeoKeyword(String brandSeoKeyword) {
        this.brandSeoKeyword = brandSeoKeyword.trim();
    }

    public String getBrandSeoDesc() {
        return brandSeoDesc;
    }

    public void setBrandSeoDesc(String brandSeoDesc) {
        this.brandSeoDesc = brandSeoDesc.trim();
    }

    public String getBrandDelflag() {
        return brandDelflag;
    }

    public void setBrandDelflag(String brandDelflag) {
        this.brandDelflag = brandDelflag;
    }

    public String getBrandCreateName() {
        return brandCreateName;
    }

    public void setBrandCreateName(String brandCreateName) {
        this.brandCreateName = brandCreateName;
    }

    /**
     * 获取品牌创建时间
     * */
    public Date getBrandCreateTime() {
        if (this.brandCreateTime != null) {
            return new Date(this.brandCreateTime.getTime());
        }
        return null;
    }
    /**
     * 设置品牌创建时间
     * */
    public void setBrandCreateTime(Date brandCreateTime) {
        if (brandCreateTime != null) {
            Date tEmp = (Date) brandCreateTime.clone();
            if (tEmp != null) {
                this.brandCreateTime = tEmp;
            }
        }
    }

    public String getBrandModifiedName() {
        return brandModifiedName;
    }

    public void setBrandModifiedName(String brandModifiedName) {
        this.brandModifiedName = brandModifiedName;
    }
    /**
     * 获取品牌修改时间
     * */
    public Date getBrandModifiedTime() {
        if (this.brandModifiedTime != null) {
            return new Date(this.brandModifiedTime.getTime());
        }
        return null;
    }
    /**
     * 设置品牌修改时间
     * */
    public void setBrandModifiedTime(Date brandModifiedTime) {
        if (brandModifiedTime != null) {
            Date tEmp = (Date) brandModifiedTime.clone();
            if (tEmp != null) {
                this.brandModifiedTime = tEmp;
            }
        }
    }

    public String getBrandDelName() {
        return brandDelName;
    }

    public void setBrandDelName(String brandDelName) {
        this.brandDelName = brandDelName;
    }
    /**
     * 获取品牌删除时间
     * */
    public Date getBrandDelTime() {
        if (this.brandDelTime != null) {
            return new Date(this.brandDelTime.getTime());
        }
        return null;
    }
    /**
     * 设置品牌删除时间
     * */
    public void setBrandDelTime(Date brandDelTime) {
        if (brandDelTime != null) {
            Date tEmp = (Date) brandDelTime.clone();
            if (tEmp != null) {
                this.brandDelTime = tEmp;
            }
        }
    }

    public String getBrandDesc() {
        return brandDesc;
    }

    public void setBrandDesc(String brandDesc) {
        this.brandDesc = brandDesc;
    }

    public String getPromIndex() {
        return promIndex;
    }

    public void setPromIndex(String promIndex) {
        this.promIndex = promIndex;
    }

    public Long getGrandBrandId() {
        return grandBrandId;
    }

    public void setGrandBrandId(Long grandBrandId) {
        this.grandBrandId = grandBrandId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
    

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((brandCreateName == null) ? 0 : brandCreateName.hashCode());
		result = prime * result + ((brandCreateTime == null) ? 0 : brandCreateTime.hashCode());
		result = prime * result + ((brandDelName == null) ? 0 : brandDelName.hashCode());
		result = prime * result + ((brandDelTime == null) ? 0 : brandDelTime.hashCode());
		result = prime * result + ((brandDelflag == null) ? 0 : brandDelflag.hashCode());
		result = prime * result + ((brandDesc == null) ? 0 : brandDesc.hashCode());
		result = prime * result + ((brandId == null) ? 0 : brandId.hashCode());
		result = prime * result + ((brandLogo == null) ? 0 : brandLogo.hashCode());
		result = prime * result + ((brandModifiedName == null) ? 0 : brandModifiedName.hashCode());
		result = prime * result + ((brandModifiedTime == null) ? 0 : brandModifiedTime.hashCode());
		result = prime * result + ((brandName == null) ? 0 : brandName.hashCode());
		result = prime * result + ((brandNickname == null) ? 0 : brandNickname.hashCode());
		result = prime * result + ((brandPromIndex == null) ? 0 : brandPromIndex.hashCode());
		result = prime * result + ((brandSeoDesc == null) ? 0 : brandSeoDesc.hashCode());
		result = prime * result + ((brandSeoKeyword == null) ? 0 : brandSeoKeyword.hashCode());
		result = prime * result + ((brandSeoTitle == null) ? 0 : brandSeoTitle.hashCode());
		result = prime * result + ((brandSort == null) ? 0 : brandSort.hashCode());
		result = prime * result + ((brandUrl == null) ? 0 : brandUrl.hashCode());
		result = prime * result + ((grandBrandId == null) ? 0 : grandBrandId.hashCode());
		result = prime * result + ((promIndex == null) ? 0 : promIndex.hashCode());
		result = prime * result + ((rateStatus == null) ? 0 : rateStatus.hashCode());
		result = prime * result + ((reason == null) ? 0 : reason.hashCode());
		result = prime * result + ((storeName == null) ? 0 : storeName.hashCode());
		result = prime * result + ((thirdId == null) ? 0 : thirdId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GoodsBrand other = (GoodsBrand) obj;
		if (brandCreateName == null) {
			if (other.brandCreateName != null)
				return false;
		} else if (!brandCreateName.equals(other.brandCreateName))
			return false;
		if (brandCreateTime == null) {
			if (other.brandCreateTime != null)
				return false;
		} else if (!brandCreateTime.equals(other.brandCreateTime))
			return false;
		if (brandDelName == null) {
			if (other.brandDelName != null)
				return false;
		} else if (!brandDelName.equals(other.brandDelName))
			return false;
		if (brandDelTime == null) {
			if (other.brandDelTime != null)
				return false;
		} else if (!brandDelTime.equals(other.brandDelTime))
			return false;
		if (brandDelflag == null) {
			if (other.brandDelflag != null)
				return false;
		} else if (!brandDelflag.equals(other.brandDelflag))
			return false;
		if (brandDesc == null) {
			if (other.brandDesc != null)
				return false;
		} else if (!brandDesc.equals(other.brandDesc))
			return false;
		if (brandId == null) {
			if (other.brandId != null)
				return false;
		} else if (!brandId.equals(other.brandId))
			return false;
		if (brandLogo == null) {
			if (other.brandLogo != null)
				return false;
		} else if (!brandLogo.equals(other.brandLogo))
			return false;
		if (brandModifiedName == null) {
			if (other.brandModifiedName != null)
				return false;
		} else if (!brandModifiedName.equals(other.brandModifiedName))
			return false;
		if (brandModifiedTime == null) {
			if (other.brandModifiedTime != null)
				return false;
		} else if (!brandModifiedTime.equals(other.brandModifiedTime))
			return false;
		if (brandName == null) {
			if (other.brandName != null)
				return false;
		} else if (!brandName.equals(other.brandName))
			return false;
		if (brandNickname == null) {
			if (other.brandNickname != null)
				return false;
		} else if (!brandNickname.equals(other.brandNickname))
			return false;
		if (brandPromIndex == null) {
			if (other.brandPromIndex != null)
				return false;
		} else if (!brandPromIndex.equals(other.brandPromIndex))
			return false;
		if (brandSeoDesc == null) {
			if (other.brandSeoDesc != null)
				return false;
		} else if (!brandSeoDesc.equals(other.brandSeoDesc))
			return false;
		if (brandSeoKeyword == null) {
			if (other.brandSeoKeyword != null)
				return false;
		} else if (!brandSeoKeyword.equals(other.brandSeoKeyword))
			return false;
		if (brandSeoTitle == null) {
			if (other.brandSeoTitle != null)
				return false;
		} else if (!brandSeoTitle.equals(other.brandSeoTitle))
			return false;
		if (brandSort == null) {
			if (other.brandSort != null)
				return false;
		} else if (!brandSort.equals(other.brandSort))
			return false;
		if (brandUrl == null) {
			if (other.brandUrl != null)
				return false;
		} else if (!brandUrl.equals(other.brandUrl))
			return false;
		if (grandBrandId == null) {
			if (other.grandBrandId != null)
				return false;
		} else if (!grandBrandId.equals(other.grandBrandId))
			return false;
		if (promIndex == null) {
			if (other.promIndex != null)
				return false;
		} else if (!promIndex.equals(other.promIndex))
			return false;
		if (rateStatus == null) {
			if (other.rateStatus != null)
				return false;
		} else if (!rateStatus.equals(other.rateStatus))
			return false;
		if (reason == null) {
			if (other.reason != null)
				return false;
		} else if (!reason.equals(other.reason))
			return false;
		if (storeName == null) {
			if (other.storeName != null)
				return false;
		} else if (!storeName.equals(other.storeName))
			return false;
		if (thirdId == null) {
			if (other.thirdId != null)
				return false;
		} else if (!thirdId.equals(other.thirdId))
			return false;
		return true;
	}

	@Override
    public String toString() {
        return "GoodsBrand{" + "brandId=" + brandId + ", brandNickname='" + brandNickname + '\'' + ", brandName='"
            + brandName + '\'' + ", brandUrl='" + brandUrl + '\'' + ", brandLogo='" + brandLogo + '\'' + ", brandSort="
            + brandSort + ", promIndex='" + promIndex + '\'' + ", brandSeoTitle='" + brandSeoTitle + '\''
            + ", brandSeoKeyword='" + brandSeoKeyword + '\'' + ", brandSeoDesc='" + brandSeoDesc + '\''
            + ", brandDelflag='" + brandDelflag + '\'' + ", brandCreateName='" + brandCreateName + '\''
            + ", brandCreateTime=" + brandCreateTime + ", brandModifiedName='" + brandModifiedName + '\''
            + ", brandModifiedTime=" + brandModifiedTime + ", brandDelName='" + brandDelName + '\'' + ", brandDelTime="
            + brandDelTime + ", brandDesc='" + brandDesc + '\'' + ", grandBrandId=" + grandBrandId + ", storeName='"
            + storeName + '\'' + ", rateStatus='" + rateStatus + '\'' + ", thirdId=" + thirdId + ", reason='" + reason
            + '\'' + ", brandPromIndex='" + brandPromIndex + '\'' + '}';
    }
}
