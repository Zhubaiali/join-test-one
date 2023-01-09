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
	private StudentInfoDao studentInfoDao;

	@Autowired
	private ClassDao classDao;

	@Autowired
	private CollegeDao collegeDao;

	@Autowired
	private MajorDao majorDao;

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

		Integer classId = studentInfo.getClassId();
		Integer collegeId = studentInfo.getTocollege();
		Integer tomajorId = studentInfo.getTomajor();
		checkDateExistence(classIds, classId, "班级");
		checkDateExistence(collegeIds, collegeId, "学院");
		checkDateExistence(majorIds, tomajorId, "专业");

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
		checkDateExistence(classIds, classId, "班级");
		checkDateExistence(collegeIds, collegeId, "学院");
		checkDateExistence(majorIds, tomajorId, "专业");

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

	private void checkDateExistence(List<Integer> ids, Integer dateId, String dateType) {
		boolean flag = true;
		for (Integer id : ids) {
			if (dateId.equals(id)) {
				flag = false;
				break;
			}
		}
		if (flag) {
			throw new RuntimeException(dateType + "不存在");
		}
	}

}
