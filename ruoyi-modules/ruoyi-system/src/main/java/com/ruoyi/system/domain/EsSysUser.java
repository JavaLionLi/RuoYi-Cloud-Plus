package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ruoyi.common.core.annotation.Sensitive;
import com.ruoyi.common.core.constant.UserConstants;
import com.ruoyi.common.core.enums.SensitiveStrategy;
import com.ruoyi.common.core.web.domain.BaseEntity;
import com.ruoyi.common.core.xss.Xss;
import com.ruoyi.system.api.domain.SysDept;
import com.ruoyi.system.api.domain.SysRole;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 用户对象 sys_user
 *
 * @author Lion Li
 */

@Data
@NoArgsConstructor
@ApiModel("用户信息")
@Document(indexName = "sys_user")
public class EsSysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID")
    @Field("id")
    @Id
    private Long id;

    /**
     * 用户账号
     */
    @ApiModelProperty(value = "用户账号")

    @Field("user_name")
    private String userName;

    /**
     * 用户昵称
     */
    @ApiModelProperty(value = "用户昵称")
    @Field("nick_name")
    private String nickName;

    /**
     * 用户类型（sys_user系统用户）
     */
    @ApiModelProperty(value = "用户类型")
    @Field("user_type")
    private String userType;


    public EsSysUser(Long id) {
        this.id = id;
    }



}
