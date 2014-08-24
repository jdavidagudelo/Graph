package example;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Scheduling {
	public static class Job implements Comparable<Job>
	{
		public String name;
		public BigDecimal weight;
		public BigDecimal length;
		public Job(String name, BigDecimal weight, BigDecimal length)
		{
			this.name = name;
			this.weight = weight;
			this.length = length;
		}
		public BigDecimal getRatio()
		{
			return weight.divide(length);
		}
		public int compareTo(Job o) {
			
			return o.getRatio().compareTo(getRatio());
		}
		public String toString()
		{
			return name;
		}
	}
	public static void main (String args[])
	{
		Scheduling s = new Scheduling();
		Job j1 = new Job("1", BigDecimal.valueOf(3), BigDecimal.valueOf(5));
		Job j2 = new Job("2", BigDecimal.valueOf(1), BigDecimal.valueOf(2));
		List<Job> jobs = s.getSortedJobs(Arrays.asList(j1, j2));
		
		System.out.println(jobs+" "+s.getCost(jobs));
	}
	public BigDecimal getCost(List<Job> jobs)
	{
		BigDecimal result = BigDecimal.ZERO;
		BigDecimal l = BigDecimal.ZERO;
		for(Job job : jobs)
		{
			l = l.add(job.length);
			result = result.add(l.multiply(job.weight));
			
		}
		return result;
	}
	public List<Job> getSortedJobs(List<Job> jobs)
	{
		Collections.sort(jobs);
		return jobs;
	}
}
