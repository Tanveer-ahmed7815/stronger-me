package com.tyss.strongameapp.dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VariantTypeDTO {

	private List<String> firstVariants;

	private List<String> secondVariants;

    private String firstVariant;
   
    private String secondVariant;

	public VariantTypeDTO(List<String> firstVariants, List<String> secondVariants) {
		super();
		this.firstVariants = firstVariants;
		this.secondVariants = secondVariants;
	}
    
}
