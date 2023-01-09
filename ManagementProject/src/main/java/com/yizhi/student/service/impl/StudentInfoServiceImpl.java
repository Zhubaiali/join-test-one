package com.yizhi.student.service.impl;

import com.yizhi.student.dao.ClassDao;
import com.yizhi.student.dao.CollegeDao;
import com.yizhi.student.dao.MajorDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.yizhi.student.dao.StudentInfoDao;
import com.yizhi.student.domain.StudentInfoDO;
import com.yizhi.student.service.StudentInfoService;



@Service
public class StudentInfoServiceImpl implements StudentInfoService {
	@Autowired
	private ClassDao classDao;

	@Autowired
	private CollegeDao collegeDao;

	@Autowired
	private MajorDao majorDao;
	@Autowired
	private StudentInfoDao studentInfoDao;
	
	@Override
	public StudentInfoDO get(Integer id){
		System.out.println("======service层中传递过来的id参数是：" + id + "======");
		return studentInfoDao.get(id);
	}


	@Override
	public List<StudentInfoDO> list(Map<String, Object> map){
		return studentInfoDao.list(map);
	}

	//"===================================================================================="


	@Override
	public int count(Map<String, Object> map){
		return studentInfoDao.count(map);
	}

	@Override
	public int save(StudentInfoDO studentInfo){
		List<Integer> classIds = classDao.getIds();
		List<Integer> collegeIds = collegeDao.getIds();
		List<Integer> majorIds = majorDao.getIds();

		if (!classIds.contains(studentInfo.getClassId())) {
			throw new RuntimeException("Invalid classId");
		}
		if (!collegeIds.contains(studentInfo.getTocollege())) {
			throw new RuntimeException("Invalid collegeId");
		}
		if (!majorIds.contains(studentInfo.getTomajor())) {
			throw new RuntimeException("Invalid tomajorId");
		}

		studentInfo.setAddTime(new Date());
		studentInfo.setAddUserid(studentInfo.getAddUserid());
		return studentInfoDao.save(studentInfo);
	}
	
	@Override
	public int update(StudentInfoDO studentInfo){
		List<Integer> classIds = classDao.getIds();
		List<Integer> collegeIds = collegeDao.getIds();
		List<Integer> majorIds = majorDao.getIds();

		Integer classId = studentInfo.getClassId();
		Integer collegeId = studentInfo.getTocollege();
		Integer tomajorId = studentInfo.getTomajor();

		if (!classIds.contains(studentInfo.getClassId())) {
			throw new RuntimeException("Invalid classId");
		}
		if (!collegeIds.contains(studentInfo.getTocollege())) {
			throw new RuntimeException("Invalid collegeId");
		}
		if (!majorIds.contains(studentInfo.getTomajor())) {
			throw new RuntimeException("Invalid tomajorId");
		}

		studentInfo.setEditTime(new Date());
		return studentInfoDao.update(studentInfo);
	}
	
	@Override
	public int remove(Integer id){
		return studentInfoDao.remove(id);
	}
	
	@Override
	public int batchRemove(Integer[] ids){
		List<Integer> idList = studentInfoDao.selectList();
		System.out.println("idList:  " + idList);
		boolean falg = false;
		for (int i = 0; i < ids.length; i++) {
			falg = false;
			for (int j = 0; j < idList.size(); j++) {
				if (ids[i].equals(idList.get(j))) {
					falg = true;
					break;
				}
			}
			if (!falg) {
				throw new RuntimeException();
			}
		}
		return studentInfoDao.batchRemove(ids);
	}
}
