package aq.progetto;

public class RestrictedSocialNetwork extends SocialNetwork{

    @Override
    protected boolean authorizePostEdit(String user, Post post) {
        return user.equals(post.getAuthor()) || user.equals("admin");
    }

}
