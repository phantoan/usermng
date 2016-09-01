package com.liferay.usermanagement.portlet;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.usermanagement.model.Department;
import com.liferay.usermanagement.model.UserInfo;
import com.liferay.usermanagement.service.DepartmentLocalServiceUtil;
import com.liferay.usermanagement.service.UserInfoLocalServiceUtil;
import com.liferay.usermanagement.service.persistence.DepartmentUtil;
import com.liferay.usermanagement.util.Role;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import org.osgi.service.component.annotations.Component;

@Component(immediate = true, property = { "com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.instanceable=true", "javax.portlet.display-name=UserManagement Portlet",
		"javax.portlet.init-param.template-path=/", "javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user" }, service = Portlet.class)
public class UserManagementPortlet extends MVCPortlet {

	public void addDepartment(ActionRequest actionRequest, ActionResponse actionResponse)
			throws PortalException, SystemException {
		ServiceContext serviceContext = ServiceContextFactory.getInstance(Department.class.getName(), actionRequest);
		String departmentCode = ParamUtil.getString(actionRequest, "departmentCode");
		departmentCode = departmentCode.toLowerCase().replaceAll("\\s+", "");
		int check = DepartmentLocalServiceUtil.countDepartmentByCode(departmentCode);
		if ( check > 0){
			System.out.println("it works");
		}
		String departmentName = ParamUtil.getString(actionRequest, "departmentName");

		String leaderCode = ParamUtil.getString(actionRequest, "leaderCode");
		leaderCode = leaderCode.toLowerCase().replaceAll("\\s+", "");

		String description = ParamUtil.getString(actionRequest, "description");

		try {
			System.out.println(departmentCode);
			System.out.println(departmentName);
			System.out.println(leaderCode);
			DepartmentLocalServiceUtil.addDepartment(departmentCode, departmentName, leaderCode, description,
					serviceContext);
			SessionMessages.add(actionRequest, "department-added");
		} catch (Exception e) {
			SessionErrors.add(actionRequest, e.getClass(), e);
			actionResponse.setRenderParameter("mvcPath", "/departmentmng/edit_department.jsp");
		}
	}

	
}
