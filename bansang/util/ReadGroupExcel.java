package org.bansang.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bansang.dto.GroupDTO;
import org.bansang.dto.GroupMemberDTO;

import lombok.extern.java.Log;

@Log
public class ReadGroupExcel {


	public GroupDTO readGroupFromExcelFile(String excelFilePath) throws IOException {
	    List<GroupMemberDTO> listGroup = new ArrayList<GroupMemberDTO>();
	    FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
	 
	    Workbook workbook = getWorkbook(inputStream, excelFilePath);
	    Sheet firstSheet = workbook.getSheetAt(0);
	    Iterator<Row> iterator = firstSheet.iterator();
	    
	    iterator.next();
	    GroupDTO groupDto = new GroupDTO();
	    
	    while (iterator.hasNext()) {
	        Row nextRow = iterator.next();
	        Iterator<Cell> cellIterator = nextRow.cellIterator();
	        
	        GroupMemberDTO groupMemberDto = new GroupMemberDTO();
	        while (cellIterator.hasNext()) {
	            Cell nextCell = cellIterator.next();
	            int columnIndex = nextCell.getColumnIndex();
	            
	            switch (columnIndex) {
	            case 0:
	            	groupDto.setGroupName((String) getCellValue(nextCell));
	                break;	           
	            case 1:
	            	groupDto.setGroupLeader((String) getCellValue(nextCell));
	            	break;
	            case 2:
	            	groupMemberDto.setMemberName((String) getCellValue(nextCell));
	            	break;
	            case 3:
	            	groupMemberDto.setMemberId((String) getCellValue(nextCell));
	            	break;
	            }
	            
	        }
	        if(groupMemberDto.getMemberName() !=null && groupMemberDto.getMemberId() != null) {
	        	listGroup.add(groupMemberDto);
	        }
	        groupMemberDto = null;
	    }  
	    groupDto.setList(listGroup);
	    groupDto.setGroupMemberCount(new Long(listGroup.size()));
	    workbook.close();
	    inputStream.close();
	 
	    return groupDto;
	}
	

	@SuppressWarnings("deprecation")
	private Object getCellValue(Cell cell) {
	    switch (cell.getCellType()) {
	    case Cell.CELL_TYPE_STRING:
	        return cell.getStringCellValue();
	 
	    case Cell.CELL_TYPE_BOOLEAN:
	        return cell.getBooleanCellValue();
	 
	    case Cell.CELL_TYPE_NUMERIC:
	        return cell.getNumericCellValue();
	    }
	    return null;
	}
	
	private Workbook getWorkbook(FileInputStream inputStream, String excelFilePath)
	        throws IOException {
	    Workbook workbook = null;
	 
	    if (excelFilePath.endsWith("xlsx")) {
	        workbook = new XSSFWorkbook(inputStream);
	    } else if (excelFilePath.endsWith("xls")) {
	        workbook = new HSSFWorkbook(inputStream);
	    } else {
	        throw new IllegalArgumentException("The specified file is not Excel file");
	    }
	 
	    return workbook;
	}
}