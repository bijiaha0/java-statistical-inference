package com.github.chen0040.si.statistics;


import com.github.chen0040.si.exceptions.VariableWrongValueTypeException;


/**
 * Created by xschen on 3/5/2017.
 * A sampling distribution is a theoretical distribution of the values that a specified statistic of a sample takes on in all of the possible samples of a specific size that can be made from a given population. We have not discussed sampling distributions before.
 * Note that we can have sampling distributions of sample means, of sample standard deviations, etc.
 */
public class SamplingDistributionOfSampleProportion {

   // the sample statistics
   private final double sampleProportion;

   private final int sampleSize;


   private final double standardError;

   public SamplingDistributionOfSampleProportion(SampleDistribution sampleDistribution) {
      if(!sampleDistribution.isNumeric()) {
         throw new VariableWrongValueTypeException("Sampling distribution for sample proportions is not defined for numeric variable");
      }

      double p = sampleDistribution.getProportion();
      this.sampleProportion = p;
      this.sampleSize = sampleDistribution.getSampleSize();

      this.standardError = calculateStandardError(p);
   }

   public SamplingDistributionOfSampleProportion(double sampleProportion, int sampleSize) {


      this.sampleProportion = sampleProportion;
      this.sampleSize = sampleSize;

      this.standardError = calculateStandardError(sampleProportion);
   }

   private double calculateStandardError(double p) {
      return p * (1 - p);
   }

   public double getSampleProportion() {
      return sampleProportion;
   }

   public double getStandardError() {
      return standardError;
   }

   public int getSampleSize(){
      return sampleSize;
   }
}
