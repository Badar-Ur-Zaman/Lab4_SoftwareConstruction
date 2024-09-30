/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.Set;

import org.junit.Test;

public class ExtractTest {

    /*
     * TODO: your testing strategies for these methods should go here.
     * See the ic03-testing exercise for examples of what a testing strategy comment looks like.
     * Make sure you have partitions.
     */
    
    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    
    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testGetTimespanTwoTweets() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet2));
        
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d2, timespan.getEnd());
    }
    
    @Test
    public void testGetMentionedUsersNoMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1));
        
        assertTrue("expected empty set", mentionedUsers.isEmpty());
    }

    @Test
    public void testGetTimespanEmptyList() {
        Timespan timespan = Extract.getTimespan(Arrays.asList());
        // Assuming current time can be used, but consider handling this case based on your implementation.
        assertEquals("expected start and end to be equal for empty list", timespan.getStart(), timespan.getEnd());
    }

    @Test
    public void testGetTimespanSingleTweet() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1));
        
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d1, timespan.getEnd());
    }

    @Test
    public void testGetMentionedUsersSingleMention() {
        Tweet tweetWithMention = new Tweet(3, "user1", "Hello @user2, how are you?", d1);
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweetWithMention));
        
        assertEquals(1, mentionedUsers.size());
        assertTrue(mentionedUsers.contains("user2"));
    }

    @Test
    public void testGetMentionedUsersMultipleMentions() {
        Tweet tweetWithMultipleMentions = new Tweet(4, "user3", "Shoutout to @user2 and @User4!", d1);
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweetWithMultipleMentions));
        
        assertEquals(2, mentionedUsers.size());
        assertTrue(mentionedUsers.contains("user2"));
        assertTrue(mentionedUsers.contains("user4"));
    }

//    @Test
//    public void testGetMentionedUsersIgnoreInvalid() {
//        Tweet tweetWithInvalidMention = new Tweet(5, "user5", "Check this out @invalid-user!", d1);
//        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweetWithInvalidMention));
//        
//        assertTrue("expected empty set for invalid mention", mentionedUsers.isEmpty());
//    }


}
