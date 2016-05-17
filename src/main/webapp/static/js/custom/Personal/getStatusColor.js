function getStatusColor(id) {
    var statusColor = '';
    switch (parseInt(id)){
        case 1:
            statusColor = '#ACD878';
            break;
        case 2:
            statusColor = '#985EFF';
            break;
        case 3:
            statusColor = '#7486E6';
            break;
        case 4:
            statusColor = '#FFB240';
            break;
        case 5:
            statusColor = '#008EFF';
            break;
        case 6:
            statusColor = '#9E9E9E';
            break;
        case 7:
            statusColor = '#FD7268';
            break;
        case 8:
            statusColor = '#00C4DA';
            break;
        case 9:
            statusColor = '#F44336';
            break;
        case 10:
            statusColor = '#4CAF50';
            break;
    }
    return statusColor;
}