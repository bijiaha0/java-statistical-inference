package com.github.chen0040.si.statistics;


import com.github.chen0040.si.exceptions.VariableWrongValueTypeException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;


/**
 * Created by xschen on 3/5/2017.
 * A sample distribution is an observed distribution of the values that a variable is observed to have for a sample of individuals. We have seen numerous sample distributions.
 */
@Getter
@Setter
public class SampleDistribution {

   private double sampleMean;
   private double sampleSd;
   private double sampleVariance;
   private boolean isNumeric;
   private int sampleSize;

   @Getter(AccessLevel.NONE)
   @Setter(AccessLevel.NONE)
   private double proportion;

   @Getter(AccessLevel.NONE)
   @Setter(AccessLevel.NONE)
   private String successLabel;

   @Setter(AccessLevel.NONE)
   private final String groupId;

   public SampleDistribution(Sample sample, String groupId){
      if(!sample.isNumeric()){
         throw new VariableWrongValueTypeException("The constructor can only work on numeric variables");
      }

      this.groupId = groupId;

      isNumeric = true;

      sampleMean = sample.getObservations().stream()
              .filter(o -> groupId == null || o.getGroupId().equals(groupId))
              .map(Observation::getNumericValue)
              .reduce((a, b) -> a + b).get() / sample.size(groupId);

      sampleVariance = sample.getObservations().stream()
              .filter(o -> groupId == null || o.getGroupId().equals(groupId))
              .map(o -> Math.pow(o.getNumericValue() - sampleMean, 2.0))
              .reduce((a, b) -> a + b).get() / (sample.size(groupId)-1);

      sampleSd = Math.sqrt(sampleVariance);

      sampleSize = sample.size(groupId);
   }

   public SampleDistribution(Sample sample, String successLabel, String groupId) {
      if(sample.isNumeric()) {
         throw new VariableWrongValueTypeException("The constructor can only work on categorical variables");
      }

      this.groupId = groupId;

      isNumeric = false;

      sampleMean = sample.size(groupId) * sample.proportion(successLabel, groupId);

      this.proportion = sample.proportion(successLabel, groupId);
      sampleVariance =  sample.size(groupId) * this.proportion * (1-this.proportion);

      sampleSd = Math.sqrt(sampleVariance);

      sampleSize = sample.size(groupId);

      this.successLabel = successLabel;
   }

   public double getProportion(){
      if(isNumeric()){
         throw new NotImplementedException();
      }
      return proportion;
   }

   public void setProportion(double p) {
      proportion = p;
   }

   public String getSuccessLabel(){
      if(isNumeric()) {
         throw new NotImplementedException();
      }

      return successLabel;
   }

   public void setSuccessLabel(String successLabel) {
      this.successLabel = successLabel;
   }


   public boolean isCategorical() {
      return !isNumeric();
   }
}
