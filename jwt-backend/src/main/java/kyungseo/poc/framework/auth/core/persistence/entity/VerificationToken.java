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

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import kyungseo.poc.framework.auth.core.model.TokenStatus;

/**
 * @author 박경서 (Kyungseo.Park@gmail.com)
 * @version 1.0
 */
@Entity(name = "VERIFICATION_TOKEN")
public class VerificationToken {

    // 회원 등록 메커니즘은 사용자를 성공적으로 등록한 후 발송해준 "등록 확인" 이메일을 통해
    // 사용자의 이메일 주소가 유효함을 확인함으로써 해당 계정을 활성화 한다.
    // 사용자는 받은 이메일을 통해 전송된 고유한 활성화 링크를 클릭함으로써 이를 수행한다.
    //
    // 이에따라 새로 등록된 사용자는 상기 프로세스가 완료될 때까지는 계정이 비활성화 된 상태여서
    // 시스템에 로그인할 수 없다.
    //
    // VerificationToken: 사용자를 확인하는 주요 아티팩트로 간단한 Token을 사용
    // VerificationToken 엔터티는 다음 기준을 충족해야 한다.
    //  - 사용자 에게 다시 연결되어야 한다(단방향 관계를 통해).
    //  - 등록 후 바로 생성된다.
    //  - 생성 후 24시간 이내에 만료된다.
    //  - 고유하고 임의로 생성된 값을 가짐

    private static final int EXPIRATION = 60 * 24;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "TOKEN", nullable = false, unique = true)
    private String token;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    //@JoinColumn(nullable = false, name = "user_id", foreignKey = @ForeignKey(name = "FK_VERIFY_USER"))
    private User user;

    @Column(name = "TOKEN_STATUS")
    @Enumerated(EnumType.STRING)
    private TokenStatus tokenStatus;

    @Column(name = "EXPIRY_DATE", nullable = false)
    private Date expiryDate;

    public VerificationToken() {
        super();
    }

    public VerificationToken(final String token) {
        super();

        this.token = token;
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }

    public VerificationToken(final String token, final User user) {
        super();

        this.token = token;
        this.user = user;
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }

    public VerificationToken(Long id, String token, User user, TokenStatus tokenStatus, Date expiryDate) {
        super();

        this.id = id;
        this.token = token;
        this.user = user;
        this.tokenStatus = tokenStatus;
        this.expiryDate = expiryDate;
    }

    public Long getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(final String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(final User user) {
        this.user = user;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(final Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public TokenStatus getTokenStatus() {
        return tokenStatus;
    }

    public void setTokenStatus(TokenStatus tokenStatus) {
        this.tokenStatus = tokenStatus;
    }

    public void setConfirmedStatus() {
        setTokenStatus(TokenStatus.STATUS_CONFIRMED);
    }

    private Date calculateExpiryDate(final int expiryTimeInMinutes) {
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(new Date().getTime());
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }

    public void updateToken(final String token) {
        this.token = token;
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getExpiryDate() == null) ? 0 : getExpiryDate().hashCode());
        result = prime * result + ((getToken() == null) ? 0 : getToken().hashCode());
        result = prime * result + ((getUser() == null) ? 0 : getUser().hashCode());
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
        final VerificationToken other = (VerificationToken) obj;
        if (getExpiryDate() == null) {
            if (other.getExpiryDate() != null) {
                return false;
            }
        } else if (!getExpiryDate().equals(other.getExpiryDate())) {
            return false;
        }
        if (getToken() == null) {
            if (other.getToken() != null) {
                return false;
            }
        } else if (!getToken().equals(other.getToken())) {
            return false;
        }
        if (getUser() == null) {
            if (other.getUser() != null) {
                return false;
            }
        } else if (!getUser().equals(other.getUser())) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Token [String=").append(token).append("]").append("[Expires").append(expiryDate).append("]");
        return builder.toString();
    }

}
