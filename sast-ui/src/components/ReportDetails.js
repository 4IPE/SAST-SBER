import React from "react";
import {useNavigate} from "react-router-dom";

function ReportDetails() {
    const report = JSON.parse(sessionStorage.getItem('report'));
    const navigate = useNavigate();

    return (
        <>
            <header className="header">
                <div className="icon" onClick={() => navigate(-1)}>
                    <i className="ui icon arrow left"></i>
                </div>
                <div className="icon" onClick={() => navigate('/user/profile')}>
                    <i className="ui icon user"></i>
                </div>
            </header>

            <div
                dangerouslySetInnerHTML={{__html: report.content}}
                className="report-content">
            </div>

        </>
    )
}

export default ReportDetails;
