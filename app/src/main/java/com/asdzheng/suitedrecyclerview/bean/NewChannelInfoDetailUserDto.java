package com.asdzheng.suitedrecyclerview.bean;

/**
 * Created by asdzheng on 2015/12/10.
 */
public class NewChannelInfoDetailUserDto extends BaseDto
{
    private String avatar;
    private String id;
    public UserMetaDto meta;
    private String username;

    public String getAvatar() {
        return this.avatar;
    }

    public String getId() {
        return this.id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setAvatar(final String avatar) {
        this.avatar = avatar;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public static class UserMetaDto
    {
        public long firstblood;

        public UserMetaDto(final long firstblood) {
            this.firstblood = -1L;
            this.firstblood = firstblood;
        }
    }
}
