function getStatusColor(id) {
    var statusColor = '';
    switch (parseInt(id)){
        case 1:
            statusColor = '#F44336';
            break;
        case 2:
            statusColor = '#00C4DA';
            break;
        case 3:
            statusColor = '#FD7268';
            break;
        case 4:
            statusColor = '#ACD878';
            break;
        case 5:
            statusColor = '#7486E6';
            break;
        case 6:
            statusColor = '#FFB240';
            break;
        case 7:
            statusColor = '#985EFF';
            break;
        case 8:
            statusColor = '#4CAF50';
            break;
        case 9:
            statusColor = '#008EFF';
            break;
    }
    return statusColor;
}