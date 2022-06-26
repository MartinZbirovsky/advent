import React from 'react'

export const AdsPageListItem = ({ ads }) => {
  return (
    <a>
      <table className='adspagelistitem'>
        <tbody>
          <tr>{ads.id}</tr>
          <tr>{ads.name}</tr>
          <tr>
            <td>{ads.salaryFrom}</td>
            <td>{ads.salaryTo}</td>
          </tr>
          <tr>{ads.officePlace}</tr>
          <tr>{ads.workType}</tr>
          <tr>{ads.publisherName}</tr>
          <tr>{ads.companyLogo}</tr>
        </tbody>
      </table>
    </a>
  )
}
