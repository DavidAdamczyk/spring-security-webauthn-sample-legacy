package net.sharplab.springframework.security.webauthn.sample.domain.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * ユーザーモデル
 */
@Entity
@Table(name = "m_user")
public class UserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(columnDefinition = "blob")
    private byte[] userHandle;
    private String firstName;
    private String lastName;
    private String emailAddress;

    @ManyToMany
    @JoinTable(
            name = "r_user_group",
            joinColumns = {@JoinColumn(name = "group_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")}

    )
    private List<GroupEntity> groups;

    @ManyToMany
    @JoinTable(
            name = "r_user_authority",
            joinColumns = {@JoinColumn(name = "authority_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")}

    )
    private List<AuthorityEntity> authorities;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AuthenticatorEntity> authenticators;

    private String password;

    private boolean locked;

    @Column(name = "pwauth_allowed")
    private boolean passwordAuthenticationAllowed;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public byte[] getUserHandle() {
        return userHandle;
    }

    public void setUserHandle(byte[] userHandle) {
        this.userHandle = userHandle;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public List<GroupEntity> getGroups() {
        return groups;
    }

    public void setGroups(List<GroupEntity> groups) {
        this.groups = groups;
    }

    public List<AuthorityEntity> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<AuthorityEntity> authorities) {
        this.authorities = authorities;
    }

    public List<AuthenticatorEntity> getAuthenticators() {
        return authenticators;
    }

    public void setAuthenticators(List<AuthenticatorEntity> authenticators) {
        this.authenticators = authenticators;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public boolean isPasswordAuthenticationAllowed() {
        return passwordAuthenticationAllowed;
    }

    public void setPasswordAuthenticationAllowed(boolean passwordAuthenticationAllowed) {
        this.passwordAuthenticationAllowed = passwordAuthenticationAllowed;
    }

    /**
     * アカウントの文字列表現。E-Mailアドレス
     *
     * @return アカウントのE-Mailアドレス
     */
    @Override
    public String toString() {
        return emailAddress;
    }

}
