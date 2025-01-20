package org.epf.hadoop.colfil2;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class UserPair implements WritableComparable<UserPair> {
    private Text user1;
    private Text user2;

    public UserPair() {
        this.user1 = new Text();
        this.user2 = new Text();
    }

    public UserPair(String user1, String user2) {
        setUsers(user1, user2);
    }

    public void setUsers(String user1, String user2) {
        if (user1.compareTo(user2) < 0) {
            this.user1.set(user1);
            this.user2.set(user2);
        } else {
            this.user1.set(user2);
            this.user2.set(user1);
        }
    }

    public Text getUser1() {
        return user1;
    }

    public Text getUser2() {
        return user2;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        user1.write(out);
        user2.write(out);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        user1.readFields(in);
        user2.readFields(in);
    }

    @Override
    public int compareTo(UserPair other) {
        int cmp = this.user1.compareTo(other.user1);
        if (cmp != 0) {
            return cmp;
        }
        return this.user2.compareTo(other.user2);
    }

    @Override
    public int hashCode() {
        return user1.hashCode() * 163 + user2.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        UserPair other = (UserPair) obj;
        return user1.equals(other.user1) && user2.equals(other.user2);
    }

    @Override
    public String toString() {
        return user1.toString() + "," + user2.toString();
    }
}
