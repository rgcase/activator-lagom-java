package sample.ws.impl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.MoreObjects;
import com.lightbend.lagom.serialization.CompressedJsonable;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

@SuppressWarnings("serial")
@Immutable
@JsonDeserialize
public class ApplicantState implements CompressedJsonable {

    
    public final boolean approved;

    @JsonCreator
    public ApplicantState() {
        this.approved = false;
    }


    @JsonCreator
    public ApplicantState(final boolean approved) {
        this.approved = approved;
    }

    @Override
    public boolean equals(@Nullable Object another) {
        if (this == another)
            return true;
        return another instanceof ApplicantState && equalTo((ApplicantState) another);
    }

    private boolean equalTo(ApplicantState another) {
        return approved == another.approved ;
    }

    @Override
    public int hashCode() {
        int h = 31;
        if(approved){
            h = h * 17 + 1;
        }else{
            h = h * 17 + 2;
        }
        return h;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper("ApplicantState").add("approved", approved).toString();
    }
    
    
}
