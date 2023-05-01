/* ============================================================================
 * KYUNGSEO.PoC > Development Templates for building Web Apps
 *
 * Copyright 2023 Kyungseo Park <Kyungseo.Park@gmail.com>
 * ----------------------------------------------------------------------------
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ========================================================================= */

package kyungseo.poc.framework.auth.core.persistence.entity;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.jboss.aerogear.security.otp.api.Base32;

import kyungseo.poc.framework.persistence.BaseEntity;

/**
 * @author 박경서 (Kyungseo.Park@gmail.com)
 * @version 1.0
 */
@Entity(name = "USER_ACCOUNT")
@Table(name = "USER_ACCOUNT", uniqueConstraints = { @UniqueConstraint(columnNames = { "email" }) })
@SuppressWarnings("serial")
public class User extends BaseEntity {

	@Id
	@Column(name = "ID", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	//@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "security_member_seq")
    //@SequenceGenerator(name = "security_member_seq", allocationSize = 1)
    private Long id;

    @Column(name = "MEMBER_NAME")
    private String membername; // 성명

    @Column(name = "USERNAME")
    private String username; // Spring Security의 'username'

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PASSWORD", length = 2048)
    private String password;

    @Column(name = "ENABLED", nullable = false)
    private Boolean enabled;

    @Column(name = "AGE")
    private Integer age;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Column(name = "COUNTRY")
    private String country;

    @Column(name = "BIRTH_DATE")
    private String birthdate;

    //@ManyToMany(fetch = FetchType.LAZY)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "USERS_ROLES", // "USER_AUTHORITY",
      joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles = new ArrayList<>();

    @Column(name = "IS_EMAIL_VERIFIED",nullable = false)
    private Boolean isEmailVerified;

    @Column(name = "IS_USING_2FA")
    private Boolean isUsing2FA;

    @Column(name = "SECRET")
    private String secret; // 2FA 키 값

    public User() {
        super();
        this.secret = Base32.random();
        this.enabled = false;
        this.isEmailVerified = false;
        this.isUsing2FA = false;
    }

    public User(User user) {
        id = user.getId();
        username = user.getUsername();
        membername = user.getMembername();
        email = user.getEmail();
        password = user.getPassword();
        enabled = user.getEnabled();
        isEmailVerified = user.getEmailVerified();
        isUsing2FA = user.getUsing2FA();
        secret = user.getSecret();
        roles = user.getRoles();
        age = user.getAge();
        phoneNumber = user.getPhoneNumber();
        country = user.getCountry();
        birthdate = user.getBirthdate();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMembername() {
        return membername;
    }

    public void setMembername(String membername) {
        this.membername = membername;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    public Boolean getEmailVerified() {
        return isEmailVerified;
    }

    public void setEmailVerified(Boolean isEmailVerified) {
        this.isEmailVerified = isEmailVerified;
    }

    public Boolean getUsing2FA() {
        return isUsing2FA;
    }

    public void setUsing2FA(Boolean isUsing2FA) {
        this.isUsing2FA = isUsing2FA;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    // ========================================================================

    public void addRole(Role role) {
        roles.add(role);
        role.getUsers().add(this);
    }

    public void addRoles(Collection<Role> roles) {
        roles.forEach(this::addRole);
    }

    public void removeRole(Role role) {
        roles.remove(role);
        role.getUsers().remove(this);
    }

    public void markVerificationConfirmed() {
        setEmailVerified(true);
        setEnabled(true);
    }

    public void activate() {
		this.enabled = true;
	}

	public void deactivate() {
		this.enabled = false;
	}

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((getEmail() == null) ? 0 : getEmail().hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User user = (User) obj;
        if (!getEmail().equals(user.getEmail())) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("User [id=")
                .append(id)
                .append(", username=").append(username)
                .append(", membername=").append(membername)
                .append(", email=").append(email)
                .append(", age=").append(age)
                .append(", phoneNumber=").append(phoneNumber)
                .append(", country=").append(country)
                .append(", birthdate=").append(birthdate)
                .append(", enabled=").append(enabled)
                .append(", isUsing2FA=").append(isUsing2FA)
                .append(", secret=").append(secret)
                .append(", roles=").append(roles)
                .append(", isEmailVerified=").append(isEmailVerified)
                .append("]");
        return builder.toString();
    }

}
