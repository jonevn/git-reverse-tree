package com.github.jonevn.git.reverse.tree.logic.domain;

import java.util.Date;
import java.util.List;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@EqualsAndHashCode
public class Branch implements Comparable<Branch> {

	private String name;

	@EqualsAndHashCode.Exclude
	private String fromBranch;

	@EqualsAndHashCode.Exclude
	private String fromCommit;

	@EqualsAndHashCode.Exclude
	private List<Commit> commits;

	public Date getEarliestCommitDate() {
		return commits.stream().sorted().map(c -> c.getCommitTime()).findFirst().orElse(new Date());
	}

	@Override
	public int compareTo(Branch other) {
		return this.getEarliestCommitDate().compareTo(other.getEarliestCommitDate());
	}
}
