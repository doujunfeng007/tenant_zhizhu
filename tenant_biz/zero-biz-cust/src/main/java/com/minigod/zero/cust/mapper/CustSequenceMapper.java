package com.minigod.zero.cust.mapper;

public interface CustSequenceMapper {

	Long queryNextSequenceIdByName(String custIdSequenceName);
}
