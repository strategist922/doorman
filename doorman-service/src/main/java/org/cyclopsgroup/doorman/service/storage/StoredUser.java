package org.cyclopsgroup.doorman.service.storage;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.cyclopsgroup.doorman.api.User;
import org.cyclopsgroup.doorman.service.security.PasswordStrategy;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

/**
 * POJO of a user in database
 *
 * @author <a href="mailto:jiaqi@cyclopsgroup.org">Jiaqi Guo</a>
 */
@Entity
@Table( name = "dm_user", uniqueConstraints = { @UniqueConstraint( columnNames = { "user_name", "activation_token" } ),
    @UniqueConstraint( columnNames = { "email_address", "activation_token" } ) } )
@NamedQueries( { @NamedQuery( name = StoredUser.QUERY_BY_NAME_OR_ID, query = "FROM StoredUser WHERE (userName = :nameOrId OR userId = :nameOrId) AND userState <> 'PENDING'" ) } )
@org.hibernate.annotations.Entity( dynamicUpdate = true )
public class StoredUser
{
    /**
     * Name of the query that looks for user based on given user name
     */
    public static final String QUERY_BY_NAME_OR_ID = "findNonPendingUserByNameOrId";

    private String activationToken;

    private String countryCode;

    private DateTime creationDate;

    private String displayName;

    private String domainName;

    private String emailAddress;

    private String languageCode;

    private DateTime lastModified;

    private DateTime lastVisit;

    private String password;

    private PasswordStrategy passwordStrategy;

    private String timeZoneId;

    private String userId;

    private String userName;

    private UserState userState;

    private UserType userType;

    /**
     * Copy values from given User POJO
     *
     * @param from Given user pojo
     */
    public void copyFrom( User from )
    {
        setCountryCode( from.getCountryCode() );
        setDisplayName( from.getDisplayName() );
        setDomainName( from.getDomainName() );
        setEmailAddress( from.getEmailAddress() );
        setLanguageCode( from.getLanguageCode() );
        setTimeZoneId( from.getTimeZoneId() );
        setUserName( from.getUserName() );
    }

    /**
     * Get the token used for user activation. If user is already activated, token value is null
     *
     * @return Token used to activate user
     */
    @Column( name = "activation_token", length = 255 )
    public String getActivationToken()
    {
        return activationToken;
    }

    /**
     * @return Country code of country that user prefers
     */
    @Column( name = "country_code", length = 8, nullable = false )
    public String getCountryCode()
    {
        return countryCode;
    }

    /**
     * @return Creation date
     */
    @Column( name = "creation_date", nullable = false )
    @Type( type = "datetime" )
    public DateTime getCreationDate()
    {
        return creationDate;
    }

    /**
     * @return Display name of user
     */
    @Column( name = "display_name", nullable = false, length = 64 )
    public String getDisplayName()
    {
        return displayName;
    }

    /**
     * @return Domain name which indicates where the user was registered in the very beginning
     */
    @Column( name = "domain_name", length = 32, nullable = false )
    public String getDomainName()
    {
        return domainName;
    }

    /**
     * @return Email address of the user
     */
    @Column( name = "email_address", nullable = false, length = 64 )
    public String getEmailAddress()
    {
        return emailAddress;
    }

    /**
     * @return Language code of language that user prefers
     */
    @Column( name = "language_code", length = 8, nullable = false )
    public String getLanguageCode()
    {
        return languageCode;
    }

    /**
     * @return Last modified timestamp of user
     */
    @Column( name = "last_modified" )
    @Type( type = "datetime" )
    public DateTime getLastModified()
    {
        return lastModified;
    }

    /**
     * @return Last visit time of the user
     */
    @Column( name = "last_visit" )
    @Type( type = "datetime" )
    public DateTime getLastVisit()
    {
        return lastVisit;
    }

    /**
     * @return Password for login
     */
    @Column( name = "password", nullable = false, length = 32 )
    public String getPassword()
    {
        return password;
    }

    /**
     * @return The way password is calculated
     */
    @Column( name = "password_strategy", length = 8, nullable = false )
    @Enumerated( EnumType.STRING )
    public PasswordStrategy getPasswordStrategy()
    {
        return passwordStrategy;
    }

    /**
     * @return Time zone user lives in
     */
    @Column( name = "time_zone_id", nullable = false, length = 32 )
    public String getTimeZoneId()
    {
        return timeZoneId;
    }

    /**
     * @return Internal user identifier of user
     */
    @Id
    @Column( name = "user_id", nullable = false, length = 32 )
    public String getUserId()
    {
        return userId;
    }

    /**
     * @return Unique name of user
     */
    @Column( name = "user_name", nullable = false, length = 64 )
    public String getUserName()
    {
        return userName;
    }

    /**
     * @return State of user model
     */
    @Column( name = "user_state", nullable = false, length = 8 )
    @Enumerated( EnumType.STRING )
    public UserState getUserState()
    {
        return userState;
    }

    /**
     * @return Type of user
     */
    @Column( name = "user_type", nullable = false, length = 8 )
    @Enumerated( EnumType.STRING )
    public UserType getUserType()
    {
        return userType;
    }

    /**
     * Set activation token of user
     *
     * @param activationToken Activation token to set
     */
    public void setActivationToken( String activationToken )
    {
        this.activationToken = activationToken;
    }

    /**
     * @param countryCode {@link #getCountryCode()}
     */
    public void setCountryCode( String countryCode )
    {
        this.countryCode = countryCode;
    }

    /**
     * @param creationDate {@link #getCreationDate()}
     */
    public void setCreationDate( DateTime creationDate )
    {
        this.creationDate = creationDate;
    }

    /**
     * @param displayName {@link #getDisplayName()}
     */
    public void setDisplayName( String displayName )
    {
        this.displayName = displayName;
    }

    /**
     * @param domainName {@link #getDomainName()}
     */
    public void setDomainName( String domainName )
    {
        this.domainName = domainName;
    }

    /**
     * @param emailAddress {@link #getEmailAddress()}
     */
    public void setEmailAddress( String emailAddress )
    {
        this.emailAddress = emailAddress;
    }

    /**
     * @param languageCode {@link #getLanguageCode()}
     */
    public void setLanguageCode( String languageCode )
    {
        this.languageCode = languageCode;
    }

    /**
     * @param lastModified {@link #getLastModified()}
     */
    public void setLastModified( DateTime lastModified )
    {
        this.lastModified = lastModified;
    }

    /**
     * @param lastVisit {@link #getLastVisit()}
     */
    public void setLastVisit( DateTime lastVisit )
    {
        this.lastVisit = lastVisit;
    }

    /**
     * @param password {@link #getPassword()}
     */
    public void setPassword( String password )
    {
        this.password = password;
    }

    /**
     * @param passwordStrategy {@link #getPasswordStrategy()}
     */
    public void setPasswordStrategy( PasswordStrategy passwordStrategy )
    {
        this.passwordStrategy = passwordStrategy;
    }

    /**
     * @param timeZoneId Time zone user lives in
     */
    public void setTimeZoneId( String timeZoneId )
    {
        this.timeZoneId = timeZoneId;
    }

    /**
     * @param userId {@link #getUserId()}
     */
    public void setUserId( String userId )
    {
        this.userId = userId;
    }

    /**
     * @param userName {@link #getUserName()}
     */
    public void setUserName( String userName )
    {
        this.userName = userName;
    }

    /**
     * @param userState {@link #getUserState()}
     */
    public void setUserState( UserState userState )
    {
        this.userState = userState;
    }

    /**
     * @param userType {@link #getUserType()}
     */
    public void setUserType( UserType userType )
    {
        this.userType = userType;
    }

    /**
     * Create User POJO from itself
     *
     * @return User POJO
     */
    public User toUser()
    {
        User user = new User();
        user.setCountryCode( getCountryCode() );
        user.setCreationDate( getCreationDate() );
        user.setDisplayName( getDisplayName() );
        user.setDomainName( getDomainName() );
        user.setEmailAddress( getEmailAddress() );
        user.setLanguageCode( getLanguageCode() );
        user.setLastVisit( getLastVisit() );
        user.setTimeZoneId( getTimeZoneId() );
        user.setUserId( getUserId() );
        user.setUserName( getUserName() );
        return user;
    }
}
